package pub.codex.apix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import pub.codex.apix.build.DocumentationContextBuild;
import pub.codex.apix.context.DocumentationContext;
import pub.codex.apix.context.RequestHandler;
import pub.codex.apix.provider.RequestHandlersProvider;
import pub.codex.apix.scan.ApiDocumentationScanner;

import java.util.List;

/**
 * 注释自动工具
 */

@Component
public class ApixBootstrapper implements SmartLifecycle {


    private RequestHandlersProvider requestHandlersProvider;

    private ApiDocumentationScanner apiDocumentationScanner;

    private DocumentationCache documentationCache;


    @Autowired
    public ApixBootstrapper(RequestHandlersProvider requestHandlersProvider,
                            ApiDocumentationScanner apiDocumentationScanner,
                            DocumentationCache documentationCache
    ) {
        this.requestHandlersProvider = requestHandlersProvider;
        this.apiDocumentationScanner = apiDocumentationScanner;
        this.documentationCache = documentationCache;
    }


    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public void start() {

        // 获取 requestHandler 信息
        List<RequestHandler> requestHandlerList = requestHandlersProvider.getRequestHandlers();

        // 创建 document上下文
        DocumentationContext documentationContext = DocumentationContextBuild.build(requestHandlerList);

        // 扫描API内容生成文档
        documentationCache.addDocumentation(apiDocumentationScanner.scan(documentationContext));

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }


}