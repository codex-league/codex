package pub.codex.apix.operation;


import pub.codex.apix.context.OperationContext;


/**
 * 读取内容应用器
 */
public interface OperationBuilderPlugin {

    void apply(OperationContext context);
}
