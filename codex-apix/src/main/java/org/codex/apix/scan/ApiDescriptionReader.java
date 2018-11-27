package org.codex.apix.scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.codex.apix.context.RequestMappingContext;
import org.codex.apix.schema.ApiDescription;
import org.codex.apix.schema.Operation;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class ApiDescriptionReader {


    private ApiOperationReader apiOperationReader;

    @Autowired
    public ApiDescriptionReader(ApiOperationReader apiOperationReader) {
        this.apiOperationReader = apiOperationReader;
    }

    /**
     * 读取API描述信息
     *
     * @param context
     */
    public List<ApiDescription> read(RequestMappingContext context) {


        PatternsRequestCondition patternsCondition = context.getPatternsCondition();

        List<ApiDescription> apiDescriptionList = newArrayList();

        for (String path : patternsCondition.getPatterns()) {

            List<Operation> operations = apiOperationReader.read(context);

            context.getApiDescriptionBuilder()
                    .setPath(path)
                    .setMethodName(context.getName())
                    .setOperations(operations);

            ApiDescription apiDescription = context.apiDescriptionBuilder().build();

            apiDescriptionList.add(apiDescription);
        }

        return apiDescriptionList;
    }


}
