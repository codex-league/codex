package pub.codex.apix.scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import pub.codex.apix.context.DescriptionContext;
import pub.codex.apix.description.DescriptionBuilderPlugin;
import pub.codex.apix.schema.ApiDescription;
import pub.codex.apix.schema.Operation;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class ApiDescriptionReader {


    @Autowired
    private List<DescriptionBuilderPlugin> descriptionBuilderPlugins;

    @Autowired
    private ApiOperationReader apiOperationReader;


    /**
     * 读取API描述信息
     *
     * @param context
     */
    public List<ApiDescription> read(DescriptionContext context) {

        PathPatternsRequestCondition patternsCondition = context.getPathPatternsCondition();

        List<ApiDescription> apiDescriptionList = newArrayList();

        for (String mappingPath : patternsCondition.getPatternValues()) {

            List<Operation> operations = apiOperationReader.read(context, mappingPath);

            // 遍历应用方法
            descriptionBuilderPlugins.stream().forEach(plugin -> {
                plugin.apply(context);
            });

            context.getApiDescriptionBuilder()
                    .setPath(mappingPath)
                    .setOperations(operations);

            apiDescriptionList.add(context.apiDescriptionBuilder().build());
        }

        return apiDescriptionList;
    }


}
