package pub.codex.apix.operation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pub.codex.apix.annotations.ApiModelProperty;
import pub.codex.apix.context.OperationContext;
import pub.codex.common.result.R;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 处理 responseMapping 的 responseBody
 * 返回responseBody返回对象的字段内容
 */
@Component
@Order(Ordered.OperationResponseParamsReader)
public class OperationResponseBodyReader implements OperationBuilderPlugin {


    @Override
    public void apply(OperationContext context) {
        // 处理responseBody 响应参数
        context.operationBuilder().setResponseBody(responseBodyHandel(context));
    }


    /**
     * 处理responseBody 响应参数
     *
     * @param context
     */
    private List<Map<String, Object>> responseBodyHandel(OperationContext context) {

        try {
            Type type = context.getReturnType().getGenericParameterType();

            if (type instanceof ParameterizedType) {
                return parseFields(getAuthenticType(type).getDeclaredFields());
            }

            if (type instanceof Class<?> && !type.equals(R.class)) {
                return parseFields(((Class<?>) type).getDeclaredFields());
            }

            return Collections.emptyList();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Class<?> getAuthenticType(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;

        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (Type actualTypeArgument : actualTypeArguments) {

            if (actualTypeArgument instanceof Class<?>) {
                return (Class<?>) actualTypeArgument;
            }

            if (actualTypeArgument instanceof ParameterizedType) {
                return getAuthenticType(actualTypeArgument);
            }
        }

        return null;
    }


    public List<Map<String, Object>> parseFields(Field... fields) {

        if (fields == null) {
            return Collections.emptyList();
        }
        return Stream.of(fields).filter(field -> !field.getName().equals("serialVersionUID")).map(field -> {


            Map<String, Object> fieldMap = setFieldBasicInfo(field);

            Type type = field.getGenericType();
            if (type instanceof ParameterizedType parameterizedType) {
                for (Type abstractType : parameterizedType.getActualTypeArguments()) {
                    if (abstractType instanceof Class<?> actualType) {
                        if (!actualType.getCanonicalName().startsWith("java.") && !actualType.getCanonicalName().startsWith("javax.")) {
                            fieldMap.put("child", parseFields(((Class<?>) abstractType).getDeclaredFields()));
                        }
                    }
                }
            }

            if (type instanceof Class<?> actualType) {
                if (!actualType.getCanonicalName().startsWith("java.") && !actualType.getCanonicalName().startsWith("javax.")) {
                    fieldMap.put("child", parseFields(actualType.getDeclaredFields()));
                }
            }
            return fieldMap;


        }).collect(Collectors.toList());

    }


    /**
     * 设置字段基本信息
     *
     * @param field
     * @return
     */
    private Map<String, Object> setFieldBasicInfo(Field field) {

        Map<String, Object> map = new HashMap<>();
        map.put("field", field.getName());
        map.put("type", field.getType().getName());
        map.put("describe", field.getName());

        ApiModelProperty apiModelProperty = AnnotationUtils.findAnnotation(field, ApiModelProperty.class);
        if (apiModelProperty != null && !StringUtils.isEmpty(apiModelProperty.describe())) {
            map.put("describe", apiModelProperty.describe());
        }
        return map;
    }

}
