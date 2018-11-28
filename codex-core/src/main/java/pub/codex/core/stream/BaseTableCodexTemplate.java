package pub.codex.core.stream;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import pub.codex.common.CodexException;

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

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new CodexException("获取配置文件失败，", e);
        }
    }

    /**
     * 编译文件名
     */
    protected static String buildFilePath(String templateName, String className, String packagePath, boolean isResources) {

        String headPackagePath;

        if (!isResources) {
            headPackagePath = "main" + File.separator + "java" + File.separator;
        } else {
            headPackagePath = "main" + File.separator + "resources" + File.separator;
        }

        return headPackagePath + packagePath.replace(".", File.separator) + File.separator + className + templateName;
    }

}
