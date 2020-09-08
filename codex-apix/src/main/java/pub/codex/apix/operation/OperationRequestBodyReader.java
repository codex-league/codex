package pub.codex.apix.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import pub.codex.apix.annotations.ApiGroup;
import pub.codex.apix.annotations.ApiModelProperty;
import pub.codex.apix.context.OperationContext;
import pub.codex.apix.wrapper.VaildComponent;

import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

/**
 * 处理 RequestMapping 的 requestBody
 * 处理非基础类型&String
 * <p>
 * 处理这几种情况
 * 所有参与计算的对象，必须被 @RequestBody标注
 * <p>
 * <p>
 * 1、@Valid ：
 * ApiModelProperty (选填)
 * Notnull（必填）
 * <p>
 * 2、@Validate(g1) ：
 * ApiModelProperty/ApiModelProperty(g1)/(空)/*ApiModelProperty(g2)*，Notnull(g1)（必填）
 * ApiModelProperty(g1)，Notnull/ Notnull(g2)/(空)(选填)
 * <p>
 * <p>
 * 3、@ApiGroup(g1) ：
 * ApiModelProperty(g1)，NotNull/(空) (选填)
 * <p>
 * <p>
 * 4、@Validate(g1) ,@ApiGroup(g1):
 * ApiModelProperty/(空)/*ApiModelProperty(g2)*，Notnull(g1)（必填）
 * ApiModelProperty(g1)，Notnull/(空)/ Notnull(g2)(选填)
 * <p>
 * <p>
 * 5、@Validate(g1) ,@ApiGroup(g2):
 * ApiModelProperty(g1)（选填）
 * ApiModelProperty(g2)（选填）
 * Notnull(g1)（必填）
 * ApiModelProperty，Notnull(g1)（必填）*4.1
 * ApiModelProperty(g1)，Notnull（选填）*4.2
 * ApiModelProperty(g2)，Notnull（选填）
 * ApiModelProperty(g1)，Notnull(g1) （必填）
 * ApiModelProperty(g2)，Notnull(g2) （选填）
 * ApiModelProperty(g1)，Notnull(g2) （选填）*4.2
 * ApiModelProperty(g2)，Notnull(g1) （必填）*5.7
 * <p>
 * 思路：">":优先级      "-->":决定
 * 0、vaild
 * 1、Validate>ApiGroup
 * 2、Validate-->Notnull （1、匹配成功，必填，且后续操作忽略）
 * -->ApiModelProperty （2、验证匹配成功，选填）
 * <p>
 * ApiGroup-->ApiModelProperty （3、Validate完全匹配失败，尝试匹配，如果成功：选填）
 *
 * @see {@ReqyestParam} 包含必填验证
 */
@Component
@Order(Ordered.OperationRequestBodyReader)
public class OperationRequestBodyReader implements OperationBuilderPlugin {


    @Autowired
    private VaildComponent vaildComponent;


    @Override
    public void apply(OperationContext context) {

        // 处理requestBody 请求参数
        requestBodyHandel(context);
    }


    /**
     * 处理requestBody 请求参数
     * <p>
     * POST的情况下处理 处理requestBody
     * 其他提交方式待定
     *
     * @param context
     */
    private void requestBodyHandel(OperationContext context) {

        // 处理POST请求
        if (HttpMethod.POST.equals(context.httpMethod()) || HttpMethod.PUT.equals(context.httpMethod())) {

            requestBodyParamsHandle(context);

        }


    }


    /**
     * 遍历处理 requestBody 参数
     * <p>
     * 虽然Spring官方 请求mapping方法只允许参数出现一个 requestBody参数，
     * 为了契合方法返回类型，还是使用遍历
     *
     * @param context
     */
    private void requestBodyParamsHandle(OperationContext context) {

        List<MethodParameter> methodParameterList = context.getParameterAnnotation(RequestBody.class);

        methodParameterList.stream().forEach(methodParameter -> {

            Map<String, Object> map = newHashMap();
            map.put("fields", paramsHandle(methodParameter));  // todo 暂时只处理了JSON的结构型数据

            context.operationBuilder().setParamsBody(map);
        });
    }


    /**
     * 处理 requestBody的参数
     *
     * @param methodParameter
     * @return
     */
    private List<Map<String, Object>> paramsHandle(MethodParameter methodParameter) {

        Annotation validAnnotation = methodParameter.getParameterAnnotation(Valid.class);
        Annotation validatedAnnotation = methodParameter.getParameterAnnotation(Validated.class);
        Annotation apiGroupAnnotation = methodParameter.getParameterAnnotation(ApiGroup.class);

        Class<?> classType = methodParameter.getParameterType(); // 参数类型
        Field[] fields = classType.getDeclaredFields();

        List<Map<String, Object>> fieldsMapList = newArrayList();
        for (Field field : fields) {
            // 如果返回结果不为空，则保存进fieldsMapList
            Map<String, Object> fieldMap = getFieldInfo(validAnnotation, validatedAnnotation, apiGroupAnnotation, field);
            if (fieldMap != null) {
                fieldsMapList.add(fieldMap);
            }
        }

        return fieldsMapList;
    }

    /**
     * 获取字段的信息
     *
     * @param validAnnotation
     * @param validatedAnnotation
     * @param apiGroupAnnotation
     * @param field
     */
    private Map<String, Object> getFieldInfo(Annotation validAnnotation,
                                             Annotation validatedAnnotation,
                                             Annotation apiGroupAnnotation,
                                             Field field) {

        if (validAnnotation != null) {
            return setFieldInfo(handleField(field), field);
        }

        if (validatedAnnotation != null || apiGroupAnnotation != null) {
            return setFieldInfo(handleField(validatedAnnotation, apiGroupAnnotation, field), field);
        }

        return null;
    }


    /**
     * 设置字段基本信息
     *
     * @param field
     * @param required
     * @return
     */
    private Map<String, Object> setFieldBasicInfo(Field field, boolean required) {

        Map<String, Object> map = newHashMap();
        map.put("field", field.getName());
        map.put("type", field.getType().getName());
        map.put("describe", field.getName());
        map.put("required", required);

        ApiModelProperty apiModelProperty =  AnnotationUtils.findAnnotation(field, ApiModelProperty.class);
        if(apiModelProperty != null && !StringUtils.isEmpty(apiModelProperty.describe())){
            map.put("describe", apiModelProperty.describe());
        }
        return map;
    }

    /**
     * 设置字段信息
     *
     * @param field
     * @return
     */
    private Map<String, Object> setFieldInfo(Boolean required, Field field) {

        // 检查字段状态，决定是否返回信息
        if (required != null) {
            return setFieldBasicInfo(field, required);
        }

        return null;
    }


    /**
     * 决定字段状态属性
     * 检查字段状态
     * <p>
     * 存在 @NotNull 必填
     * 只存在 @ApiModelProperty 选填
     * 无状态则 不显示
     *
     * @param field 待处理的字段信息
     * @return true:必填 false:选填 null:不现实字段
     */
    private Boolean handleField(Field field) {


        // 查询 NotNul等包装器是否存在，则必填
        long count = vaildComponent.findValidAnnotation(field);
        if (count > 0) {
            return true;
        }

        // 只存在 @ApiModelProperty, 则选填
        Annotation ApiModelProperty = field.getAnnotation(ApiModelProperty.class);
        if (ApiModelProperty != null) {
            return false;
        }

        //  不显示
        return null;
    }


    /**
     * @param field 待处理的字段信息
     * @return true:必填 false:选填 null:不现实字段
     */
    private Boolean handleField(Annotation vaildateAnnotation, Annotation apiGroupAnnotation, Field field) {

        ApiModelProperty ApiModelProperty = field.getAnnotation(ApiModelProperty.class);

        // 1、存在vaildateAnnotation
        if (vaildateAnnotation != null) {

            Class<?>[] validatedGroup = ((Validated) vaildateAnnotation).value();

            // 如果存在字段状态存在 @NotNull,  并在相同的组中，则必填
            if (vaildComponent.matchVaild(field, validatedGroup)) {
                return true;
            }

            // 如果@Notnull等，没有匹配上，那尝试匹配 ApiModelProperty， 并在相同的组中，则选填
            if (ApiModelProperty != null && marchClassArray(ApiModelProperty.groups(), validatedGroup)) {
                return false;
            }
        }

        // 2、Validate完全匹配失败，尝试匹配，如果成功：选填
        if (ApiModelProperty != null && apiGroupAnnotation != null) {

            Class<?>[] apiGroupGroup = ((ApiGroup) apiGroupAnnotation).value();
            if (marchClassArray(ApiModelProperty.groups(), apiGroupGroup)) {
                return false;
            }
        }

        return null;
    }


    /**
     * 检查两个数组有没有相同的对象
     *
     * @param aClass
     * @param bClass
     * @return
     */
    private boolean marchClassArray(Class<?>[] aClass, Class<?>[] bClass) {

        long index = Arrays.stream(aClass).filter(a -> {
            long count = Arrays.stream(bClass).filter(b -> a.equals(b)).count();
            return count > 0;
        }).count();

        return index > 0;
    }


}
