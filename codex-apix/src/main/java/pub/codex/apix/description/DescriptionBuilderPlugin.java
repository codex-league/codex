package pub.codex.apix.description;


import pub.codex.apix.context.DescriptionContext;


/**
 * 读取描述内容应用器
 */
public interface DescriptionBuilderPlugin {

    void apply(DescriptionContext context);
}
