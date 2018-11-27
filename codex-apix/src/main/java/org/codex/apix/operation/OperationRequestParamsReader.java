package org.codex.apix.operation;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.codex.apix.annotations.factory.ApiParamFactory;
import org.codex.apix.annotations.factory.RequestParamFactory;
import org.codex.apix.context.OperationContext;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;


/**
 * 处理 RequestMapping 的 params
 * 处理基础类型&String
 *
 * @see @ReqyestParam 包含必填验证
 */
@Component
@Order(Ordered.OperationRequestParamsReader)
public class OperationRequestParamsReader implements OperationBuilderPlugin {


    @Override
    public void apply(OperationContext context) {
        // @RequestParam 处理
        requestParamHandel(context);


    }

    /**
     * {@RequestParam} 处理
     * <p>
     * 1、获取mapping 基本类型或 {String}类型
     *
     * @param context
     */
    private void requestParamHandel(OperationContext context) {
        List<Map<String, Object>> params = newArrayList();

        context.getParameter().stream().forEach(methodParameter -> {

            methodParameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());

            // 设置默认信息
            Map<String, Object> map = newHashMap();
            map.put("field", methodParameter.getParameterName());
            map.put("type", methodParameter.getParameterType().getName());
            map.put("describe", methodParameter.getParameterName());
            map.put("required", Boolean.FALSE);

            // 获取注解上的描述信息
            String describe = ApiParamFactory.getDescribe(methodParameter);
            if (!StringUtils.isEmpty(describe)) {
                map.put("describe", describe);
            }

            Boolean required = RequestParamFactory.getRequired(methodParameter);
            if (required != null) {
                map.put("required", required);
            }

            // 获取类型
            params.add(map);
        });

        context.operationBuilder().setParams(params);
    }


}
