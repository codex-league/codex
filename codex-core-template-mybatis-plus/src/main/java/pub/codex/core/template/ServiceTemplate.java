package pub.codex.core.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.codex.common.utils.DateUtil;
import pub.codex.core.template.stream.BaseTemplateConfigProvider;
import pub.codex.core.template.stream.template.TableCodexTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Service类coding 模板
 */
@Component
public class ServiceTemplate extends TableCodexTemplate {


    private final String TEMPLATE_NAME = "Service.java";


    @Autowired
    private BaseTemplateConfigProvider baseTemplateConfigProvider;


    @Override
    public void coding() {

        //表名转换成Java类名
        String servicePackagePath = baseTemplateConfigProvider.getServicePath();
        String entityPackagePath = baseTemplateConfigProvider.getEntityPath();
        String datetime = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
        String comments = tableEntity.getComments();
        String className = tableEntity.getClassName();

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("servicePackagePath", servicePackagePath);
        map.put("entityPackagePath", entityPackagePath);
//        map.put("author", author);
//        map.put("email", email);
        map.put("datetime", datetime);
        map.put("comments", comments);
        map.put("className", className);
        buildTemplate(TEMPLATE_NAME, map, buildDBFilePath(TEMPLATE_NAME, className,
                servicePackagePath));
    }
}
