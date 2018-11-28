package org.codex.apix.builder;

import org.codex.apix.context.DocumentationContext;
import org.codex.apix.context.RequestHandler;

import java.util.List;

/**
 * 文档结果构建工具
 */
public class DocumentationContextBuild {


    /**
     * 构建 DocumentationContext，并初始化默认数据
     *
     * @return
     */
    public static DocumentationContext build(List<RequestHandler> handlerMappings) {
        return new DocumentationContext(handlerMappings);
    }
}
