package pub.codex.apix.provider;


import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import pub.codex.apix.context.RequestHandler;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.FluentIterable.from;

@Component
public class RequestHandlersProvider {


    @Autowired
    private final List<RequestMappingInfoHandlerMapping> handlerMappings;

    @Autowired
    public RequestHandlersProvider(List<RequestMappingInfoHandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    /**
     * 获取restful api映射对象
     *
     * @return
     */
    public List<RequestHandler> getRequestHandlers() {
        return from(handlerMappings).transformAndConcat(toMappingEntries()).transform(toRequestHandler()).toList();
    }


    private Function<RequestMappingInfoHandlerMapping,
            Iterable<Map.Entry<RequestMappingInfo, HandlerMethod>>> toMappingEntries() {
        return new Function<RequestMappingInfoHandlerMapping, Iterable<Map.Entry<RequestMappingInfo, HandlerMethod>>>() {
            @Override
            public Iterable<Map.Entry<RequestMappingInfo, HandlerMethod>> apply(RequestMappingInfoHandlerMapping input) {
                return input.getHandlerMethods().entrySet(); // 获取mapping方法
            }
        };
    }


    private Function<Map.Entry<RequestMappingInfo, HandlerMethod>, RequestHandler> toRequestHandler() {
        return new Function<Map.Entry<RequestMappingInfo, HandlerMethod>, RequestHandler>() {
            @Override
            public RequestHandler apply(Map.Entry<RequestMappingInfo, HandlerMethod> input) {
                return new RequestHandler(input.getKey(), input.getValue());
            }
        };
    }


}
