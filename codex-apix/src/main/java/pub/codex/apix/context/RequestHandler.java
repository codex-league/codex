package pub.codex.apix.context;

import com.google.common.base.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import pub.codex.apix.annotations.Api;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestHandler {


    private final RequestMappingInfo requestMapping;

    private final HandlerMethod handlerMethod;


    public RequestHandler(RequestMappingInfo requestMapping, HandlerMethod handlerMethod) {
        this.requestMapping = requestMapping;
        this.handlerMethod = handlerMethod;
    }


    /**
     * 获取API名称
     *
     * @return
     */
    public String getName() {
        return handlerMethod.getMethod().getName();
    }

    /**
     * 查询注解是否存在
     *
     * @param annotation
     * @return
     */
    public boolean isAnnotatedWith(Class<? extends Annotation> annotation) {
        return null != AnnotationUtils.findAnnotation(handlerMethod.getMethod(), annotation);
    }

    /**
     * 查询注解信息
     *
     * @param annotation
     * @param <T>
     * @return
     */
    public <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation) {
        return Optional.fromNullable(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), annotation));
    }


    /**
     * 查询参数上的对象信息
     *
     * @param annotation 按注解过滤
     * @return
     */
    public List<MethodParameter> getParameterAnnotation(Class<? extends Annotation> annotation) {

        List<MethodParameter> annotationList = Arrays.stream(handlerMethod.getMethodParameters())
                .filter(params -> params.getParameterAnnotation(annotation) != null)
                .collect(Collectors.toList());

        return annotationList;
    }

    /**
     * 查询参数上的对象信息
     * 按 基本类型 或 {String} 过滤
     *
     * @return
     */
    public List<MethodParameter> getParameter() {

        List<MethodParameter> annotationList = Arrays.stream(handlerMethod.getMethodParameters())
                .filter(params -> {
                    Class<?> c = params.getParameterType();
                    return c.equals(String.class)
                            || c.equals(Integer.class)
                            || c.equals(Long.class)
                            || c.equals(Short.class)
                            || c.equals(Character.class)
                            || c.equals(Byte.class)
                            || c.equals(Boolean.class)
                            || c.equals(Double.class)
                            || c.equals(Float.class);
                })
                .collect(Collectors.toList());

        return annotationList;
    }


    /**
     * 获取Mapping bean类型
     *
     * @return
     */
    public Class<?> declaringClass() {
        return handlerMethod.getBeanType();
    }


    /**
     * 获取API组名称
     *
     * @return
     */
    public String groupName() {

        Annotation annotation = AnnotationUtils.getAnnotation(handlerMethod.getBeanType(), Api.class);
        if (annotation != null) {
            return ((Api) annotation).describe();
        }

        return controllerNameAsGroup(handlerMethod);
    }

    private static String controllerNameAsGroup(HandlerMethod handlerMethod) {
        Class<?> controllerClass = handlerMethod.getBeanType();
        return splitCamelCase(controllerClass.getSimpleName(), "-")
                .replace("/", "")
                .toLowerCase();
    }

    /**
     * 获取方法
     *
     * @return
     */
    public Set<RequestMethod> supportedMethods() {
        return requestMapping.getMethodsCondition().getMethods();
    }

    /**
     * 获取请求状态
     *
     * @return
     */
    public PathPatternsRequestCondition getPathPatternsCondition() {
        return requestMapping.getPathPatternsCondition();
    }


    private static String splitCamelCase(String s, String separator) {
        if (s == null || s.equals("")) {
            return "";
        }
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                separator
        );
    }

    public MethodParameter getReturnType(){
        return handlerMethod.getReturnType();
    }


}
