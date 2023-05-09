package pub.codex.apix.context;

import com.google.common.base.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import pub.codex.apix.build.ApiDescriptionBuilder;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public class DescriptionContext {


    private RequestHandler handler;

    private final ApiDescriptionBuilder apiDescriptionBuilder;


    public DescriptionContext(RequestHandler handler) {
        this.handler = handler;
        this.apiDescriptionBuilder = new ApiDescriptionBuilder();
    }


    public String getName() {
        return handler.getName();
    }

    public PathPatternsRequestCondition getPathPatternsCondition() {
        return handler.getPathPatternsCondition();
    }

    public ApiDescriptionBuilder getApiDescriptionBuilder() {
        return apiDescriptionBuilder;
    }

    public Set<RequestMethod> getMethodsCondition() {
        return handler.supportedMethods();
    }

    public <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation) {
        return handler.findAnnotation(annotation);
    }

    public List<MethodParameter>  getParameterAnnotation(Class<? extends Annotation> annotation) {
        return handler.getParameterAnnotation(annotation);
    }
    public List<MethodParameter>  getParameter() {
        return handler.getParameter();
    }

    public ApiDescriptionBuilder apiDescriptionBuilder() {
        return apiDescriptionBuilder;
    }

    public MethodParameter getReturnType(){
        return handler.getReturnType();
    }


}
