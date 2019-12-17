package pub.codex.apix.annotations.factory;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;

public class PathVariableFactory {


    /**
     * 获取 RequestParam 的必填信息
     *
     * @param methodParameter
     * @return
     */
    public static Boolean getRequired(MethodParameter methodParameter) {
        Annotation requestParamAnnotation = methodParameter.getParameterAnnotation(PathVariable.class);
        if (requestParamAnnotation != null) {
            return ((PathVariable) requestParamAnnotation).required();
        }

        return null;
    }


}
