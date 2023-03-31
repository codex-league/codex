package pub.codex.core.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.codex.common.utils.DateUtil;
import pub.codex.common.db.entity.ColumnEntity;
import pub.codex.core.template.stream.BaseTemplateConfigProvider;
import pub.codex.core.template.stream.template.TableCodexTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity类coding 模板
 */
@Component
public class EntityTemplate extends TableCodexTemplate {

    private final String TEMPLATE_NAME = "Entity.java";


    @Autowired
    private BaseTemplateConfigProvider baseTemplateConfigProvider;


    @Override
    public void coding() {

        //表名转换成Java类名
        String entityPackagePath = baseTemplateConfigProvider.getEntityPath();
        String datetime = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
        String comments = tableEntity.getComments();
        String tableName = tableEntity.getTableName();
        String className = tableEntity.getClassName();
        ColumnEntity pk = tableEntity.getPk();
        List<ColumnEntity> columns = tableEntity.getColumns();

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("entityPackagePath", entityPackagePath);
        map.put("datetime", datetime);
        map.put("comments", comments);
        map.put("tableName", tableName);
        map.put("className", className);
        map.put("columns", columns);
        map.put("pk", pk);


        buildTemplate(TEMPLATE_NAME, map, buildEntityFilePath(TEMPLATE_NAME, className,
                entityPackagePath));
    }


}
