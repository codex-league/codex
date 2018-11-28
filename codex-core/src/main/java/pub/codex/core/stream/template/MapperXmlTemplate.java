package pub.codex.core.stream.template;

import pub.codex.common.db.entity.ColumnEntity;
import pub.codex.core.provider.ConfigProvider;
import pub.codex.core.stream.TableCodexTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mapper XML coding 模板
 */
@Component
public class MapperXmlTemplate extends TableCodexTemplate {


    private final String TEMPLATE_NAME = "Mapper.xml";


    @Autowired
    private ConfigProvider configProvider;


    @Override
    public void coding() {

        //表名转换成Java类名
        String entityPackagePath = configProvider.getPackageInfo().getEntityPath();
        String mapperPackagePath = configProvider.getPackageInfo().getMapperPath();
        String comments = tableEntity.getComments();
        String className = tableEntity.getClassName();
        String classname = tableEntity.getClassname();
        List<ColumnEntity> columns = tableEntity.getColumns();


        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("entityPackagePath", entityPackagePath);
        map.put("mapperPackagePath", mapperPackagePath);
        map.put("comments", comments);
        map.put("className", className);
        map.put("classname", classname);
        map.put("columns", columns);
        buildTemplate(TEMPLATE_NAME, map, buildFilePath(TEMPLATE_NAME, className,
                configProvider.getPackageInfo().getMapperXMLPath(), true));
    }


}
