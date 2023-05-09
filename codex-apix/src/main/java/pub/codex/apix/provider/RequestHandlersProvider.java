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
import java.util.stream.Collectors;

import static com.google.common.collect.FluentIterable.from;

@Component
public class RequestHandlersProvider {


    @Autowired
    private List<RequestMappingInfoHandlerMapping> handlerMappings;


    /**
     * 获取restful api映射对象
     *
     * @return
     */
    public List<RequestHandler> getRequestHandlers() {
        return handlerMappings.stream()
                .flatMap(mapping -> mapping.getHandlerMethods().entrySet().stream())
                .map(entry -> new RequestHandler(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }




}
