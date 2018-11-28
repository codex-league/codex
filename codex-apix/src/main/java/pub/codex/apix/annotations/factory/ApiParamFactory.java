package pub.codex.apix.annotations.factory;

import org.springframework.core.MethodParameter;
import pub.codex.apix.annotations.ApiParam;

import java.lang.annotation.Annotation;

public class ApiParamFactory {


    /**
     * 获取 ApiParam 的描述信息
     *
     * @param methodParameter
     * @return
     */
    public static String getDescribe(MethodParameter methodParameter) {
        Annotation apiParamAnnotation = methodParameter.getParameterAnnotation(ApiParam.class);
        if (apiParamAnnotation != null) {

            return ((ApiParam) apiParamAnnotation).describe();
        }
        return null;
    }


}
