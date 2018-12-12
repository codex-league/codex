package pub.codex.core.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.codex.common.utils.DateUtil;
import pub.codex.core.template.stream.BaseTemplateConfigProvider;
import pub.codex.core.template.stream.template.TableCodexTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapper类coding 模板
 */
@Component
public class MapperTemplate extends TableCodexTemplate {


    private final String TEMPLATE_NAME = "Mapper.java";

    @Autowired
    private BaseTemplateConfigProvider baseTemplateConfigProvider;

    @Override
    public void coding() {

        //表名转换成Java类名
        String mapperPackagePath = baseTemplateConfigProvider.getMapperPath();
        String entityPackagePath = baseTemplateConfigProvider.getEntityPath();
        String datetime = DateUtil.getDateTime();
        String comments = tableEntity.getComments();
        String className = tableEntity.getClassName();


        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("mapperPackagePath", mapperPackagePath);
        map.put("entityPackagePath", entityPackagePath);
//        map.put("author", author);
//        map.put("email", email);
        map.put("datetime", datetime);
        map.put("comments", comments);
        map.put("className", className);
        buildTemplate(TEMPLATE_NAME, map, buildFilePath(TEMPLATE_NAME, className,
                mapperPackagePath, false, false));
    }
}
