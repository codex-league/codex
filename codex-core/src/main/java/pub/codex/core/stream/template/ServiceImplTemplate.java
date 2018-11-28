package pub.codex.core.stream.template;

import pub.codex.common.DateUtil;
import pub.codex.core.provider.ConfigProvider;
import pub.codex.core.stream.TableCodexTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ServiceImpl类coding 模板
 */
@Component
public class ServiceImplTemplate extends TableCodexTemplate {


    private final String TEMPLATE_NAME = "ServiceImpl.java";

    @Autowired
    private ConfigProvider configProvider;


    @Override
    public void coding() {

        //表名转换成Java类名
        String entityPackagePath = configProvider.getPackageInfo().getEntityPath();
        String mapperPackagePath = configProvider.getPackageInfo().getMapperPath();
        String servicePackagePath = configProvider.getPackageInfo().getServicePath();
        String datetime = DateUtil.getDateTime();
        String comments = tableEntity.getComments();
        String className = tableEntity.getClassName();
        String classname = tableEntity.getClassname();


        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("entityPackagePath", entityPackagePath);
        map.put("mapperPackagePath", mapperPackagePath);
        map.put("servicePackagePath", servicePackagePath);
//        map.put("author", author);
//        map.put("email", email);
        map.put("datetime", datetime);
        map.put("comments", comments);
        map.put("className", className);
        map.put("classname", classname);
        buildTemplate(TEMPLATE_NAME, map, buildFilePath(TEMPLATE_NAME, className,
                configProvider.getPackageInfo().getServiceImplPath(), false));
    }
}
