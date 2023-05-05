package pub.codex.core.template.stream.template;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import pub.codex.common.CodexException;
import pub.codex.common.db.entity.ColumnEntity;
import pub.codex.common.db.entity.TableEntity;
import pub.codex.common.utils.DateUtil;
import pub.codex.core.template.stream.BaseTemplateConfigProvider;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * TABLE相关的 实现的抽象类
 * 根据 数据库表 coding 的template 写这里
 *
 * @author xuxi
 */


public abstract class TableCodexTemplate extends BaseTableCodexTemplate {

    /**
     * 对应的velocity在resources中的模板名称
     *
     * @return
     */
    public abstract String templateName();

    /**
     * 对应使用者自定义环境变量字段
     *
     * @return
     */
    public abstract Map<String, Object> environmentMap();

    /**
     * 对应生成代码后的存储路径
     *
     * @return
     */
    public abstract String storagePath();


    /**
     * build 信息
     */
    public void build(BaseTemplateConfigProvider baseTemplateConfigProvider, TableEntity tableEntity, ZipOutputStream zip) {
        this.baseTemplateConfigProvider = baseTemplateConfigProvider;
        this.tableEntity = tableEntity;
        this.zip = zip;
        coding();
    }


    /**
     * coding 的主要逻辑在这里实现
     */
    public void coding() {

        String TEMPLATE_NAME = templateName();
        Map<String, Object> environmentMap = environmentMap();

        buildTemplate(TEMPLATE_NAME, environmentMap, storagePath());
    }

    /**
     * 渲染模板
     *
     * @param templateName
     * @param environmentMap
     * @param filepath
     */
    protected void buildTemplate(String templateName, Map<String, Object> environmentMap, String filepath) {

        // 判断是否生成controller
        if (!tableEntity.isController() && templateName.contains("Controller")) {
            return;
        }

        // 合并默认的环境变量
        if (!environmentMap.isEmpty()) {
            environmentMap.putAll(getDefaultEnvironmentMap());
        } else {
            environmentMap = getDefaultEnvironmentMap();
        }

        // 渲染模板
        Template template = getTemplate(templateName);
        VelocityContext context = new VelocityContext(environmentMap);
        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        // 添加到zip
        try {
            zip.putNextEntry(new ZipEntry(filepath));
            IOUtils.write(sw.toString(), zip, "UTF-8");
            IOUtils.closeQuietly(sw);
            zip.closeEntry();
        } catch (IOException e) {
            throw new CodexException("渲染模板失败，表名：" + tableEntity.getClassName(), e);
        }
    }

    private Map<String, Object> getDefaultEnvironmentMap() {
        // 表名转换成Java类名

        String controllerPackagePath = baseTemplateConfigProvider.getControllerPath();
        String servicePackagePath = baseTemplateConfigProvider.getServicePath();
        String entityPackagePath = baseTemplateConfigProvider.getEntityPath();
        String mapperPackagePath = baseTemplateConfigProvider.getMapperPath();

        String datetime = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
        String comments = tableEntity.getComments();
        String tableName = tableEntity.getTableName();
        String className = tableEntity.getClassName();
        String classname = tableEntity.getClassname();

        ColumnEntity pk = tableEntity.getPk();

        List<String> interfaceType = tableEntity.getInterfaceType();
        List<ColumnEntity> columns = tableEntity.getColumns();

        // 封装模板数据
        Map<String, Object> defaultEnvironmentMap = new HashMap<>();
        defaultEnvironmentMap.put("controllerPackagePath", controllerPackagePath);
        defaultEnvironmentMap.put("servicePackagePath", servicePackagePath);
        defaultEnvironmentMap.put("entityPackagePath", entityPackagePath);
        defaultEnvironmentMap.put("mapperPackagePath", mapperPackagePath);
        defaultEnvironmentMap.put("datetime", datetime);
        defaultEnvironmentMap.put("comments", comments);
        defaultEnvironmentMap.put("tableName", tableName);
        defaultEnvironmentMap.put("className", className);
        defaultEnvironmentMap.put("classname", classname);
        defaultEnvironmentMap.put("interfaceType", interfaceType);
        defaultEnvironmentMap.put("columns", columns);
        defaultEnvironmentMap.put("pk", pk);

        return defaultEnvironmentMap;
    }
}
