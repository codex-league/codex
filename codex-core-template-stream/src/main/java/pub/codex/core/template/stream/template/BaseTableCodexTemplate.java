package pub.codex.core.template.stream.template;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.util.Properties;

/**
 * TABLE相关的 实现
 *
 * @author xuxi
 */
public class BaseTableCodexTemplate {

    /**
     * 根据名称获取模板
     *
     * @param templateName
     * @return
     */
    protected Template getTemplate(String templateName) {
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //模板路径
        final String TEMPLATE_PATH = String.format("template/%s.vm", templateName);
        Template template = Velocity.getTemplate(TEMPLATE_PATH, "UTF-8");

        return template;
    }

    protected String buildControllerFilePath(String templateName, String className, String packagePath){
        return buildFilePath(templateName, className, packagePath, "controller");
    }

    protected String buildEntityFilePath(String templateName, String className, String packagePath){
        return buildFilePath(templateName, className, packagePath, "entity");
    }

    protected String buildResourcesFilePath(String templateName, String className, String packagePath){
        return buildFilePath(templateName, className, packagePath, "resources");
    }

    protected String buildDBFilePath(String templateName, String className, String packagePath){
        return buildFilePath(templateName, className, packagePath, "db");
    }

    /**
     * 编译文件名
     */
    protected String buildFilePath(String templateName, String className, String packagePath, String prePackagePath) {

        String headPackagePath;

        headPackagePath = className + "'code" + File.separator;

        headPackagePath += prePackagePath + File.separator;

        return headPackagePath + packagePath.replace(".", File.separator) + File.separator + className + templateName;
    }

}
