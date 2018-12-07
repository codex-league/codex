package pub.codex.core.template;

import org.springframework.beans.factory.annotation.Autowired;
import pub.codex.common.DateUtil;
import pub.codex.common.db.entity.ColumnEntity;
import pub.codex.core.template.stream.BaseTemplateConfigProvider;
import pub.codex.core.template.stream.template.TableCodexTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerTemplate extends TableCodexTemplate {

    private final String TEMPLATE_NAME = "Controller.java";

    @Autowired
    private BaseTemplateConfigProvider baseTemplateConfigProvider;

    @Override
    public void coding() {
        //表名转换成Java类名
        String controllerPackagePath = baseTemplateConfigProvider.getControllerPath();
        String servicePackagePath = baseTemplateConfigProvider.getServicePath();
        String entityPackagePath = baseTemplateConfigProvider.getEntityPath();
        String datetime = DateUtil.getDateTime();
        String comments = tableEntity.getComments();
        String tableName = tableEntity.getTableName();
        String className = tableEntity.getClassName();
        String classname = tableEntity.getClassname();
        List<ColumnEntity> columns = tableEntity.getColumns();


        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("controllerPackagePath", controllerPackagePath);
        map.put("servicePackagePath", servicePackagePath);
        map.put("entityPackagePath", entityPackagePath);
        map.put("datetime", datetime);
        map.put("comments", comments);
        map.put("tableName", tableName);
        map.put("className", className);
        map.put("classname", classname);
        map.put("columns", columns);
        buildTemplate(TEMPLATE_NAME, map, buildFilePath(TEMPLATE_NAME, className,
                controllerPackagePath, false));
    }
}
