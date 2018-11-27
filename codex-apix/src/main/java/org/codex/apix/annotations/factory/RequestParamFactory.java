package org.codex.apix.annotations.factory;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;

public class RequestParamFactory {


    /**
     * 获取 RequestParam 的必填信息
     *
     * @param methodParameter
     * @return
     */
    public static Boolean getRequired(MethodParameter methodParameter) {
        Annotation requestParamAnnotation = methodParameter.getParameterAnnotation(RequestParam.class);
        if (requestParamAnnotation != null) {
            return ((RequestParam) requestParamAnnotation).required();
        }

        return null;
    }


}
