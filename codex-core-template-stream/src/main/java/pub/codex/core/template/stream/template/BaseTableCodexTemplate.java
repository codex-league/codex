package pub.codex.core.template.stream.template;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import pub.codex.common.db.entity.TableEntity;
import pub.codex.core.template.stream.BaseTemplateConfigProvider;

import java.io.File;
import java.util.Properties;
import java.util.zip.ZipOutputStream;

/**
 * TABLE相关的 实现
 *
 * @author xuxi
 */
public class BaseTableCodexTemplate {

    /**
     * 模板配置信息
     */
    protected BaseTemplateConfigProvider baseTemplateConfigProvider;

    /**
     * 表信息
     */
    protected TableEntity tableEntity;

    /**
     * 压缩包信息
     */
    protected ZipOutputStream zip;


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

    protected String buildControllerFilePath(String templateName){
        return buildFilePath(templateName, baseTemplateConfigProvider.getControllerPath(), "controller");
    }

    protected String buildEntityFilePath(String templateName){
        return buildFilePath(templateName, baseTemplateConfigProvider.getEntityPath(), "entity");
    }

    protected String buildMapperXMLFilePath(String templateName){
        return buildFilePath(templateName, baseTemplateConfigProvider.getMapperXMLPath(), "resources");
    }

    protected String buildMapperFilePath(String templateName){
        return buildFilePath(templateName, baseTemplateConfigProvider.getMapperPath(), "db");
    }
    protected String buildServiceFilePath(String templateName){
        return buildFilePath(templateName, baseTemplateConfigProvider.getServicePath(), "db");
    }
    protected String buildServiceImplFilePath(String templateName){
        return buildFilePath(templateName, baseTemplateConfigProvider.getServiceImplPath(), "db");
    }

    /**
     * 编译文件名
     */
    protected String buildFilePath(String templateName, String packagePath, String prePackagePath) {

        String className = tableEntity.getClassName();

        String headPackagePath;

        headPackagePath = className + "'code" + File.separator;

        headPackagePath += prePackagePath + File.separator;

        return headPackagePath + packagePath.replace(".", File.separator) + File.separator + className + templateName;
    }

}
