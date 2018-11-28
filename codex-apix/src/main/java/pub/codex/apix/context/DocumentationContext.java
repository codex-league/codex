package pub.codex.apix.context;

import java.util.List;

/**
 * 文档元数据上下文
 */
public class DocumentationContext {

    /**
     * RequestHandler 元信息
     */
    private final List<RequestHandler> handlerMappings;

    public DocumentationContext(List<RequestHandler> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    public List<RequestHandler> getRequestHandlers() {
        return handlerMappings;
    }
}
