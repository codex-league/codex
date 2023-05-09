package pub.codex.apix.operation;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pub.codex.apix.annotations.ApiModelProperty;
import pub.codex.apix.context.OperationContext;
import pub.codex.common.result.R;
import pub.codex.common.result.RData;
import pub.codex.common.result.RPage;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

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

            if(type.equals(R.class)){
                return Collections.emptyList();
            }

            if (type instanceof ParameterizedType parameterizedType) {


                if (type.getTypeName().contains("RData") || type.getTypeName().contains("RPage")) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (Type actualTypeArgument : actualTypeArguments) {
                        return buildFields(List.of(((Class<?>) actualTypeArgument).getDeclaredFields()));
                    }
                }

                return Collections.emptyList();
            }

            return buildFields(List.of(((Class<?>) type).getDeclaredFields()));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


    public List<Map<String, Object>> buildFields(List<Field> fields) {
        return fields.stream().map(field -> setFieldBasicInfo(field)).collect(Collectors.toList());

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
