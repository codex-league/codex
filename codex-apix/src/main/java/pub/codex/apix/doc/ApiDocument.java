package pub.codex.apix.doc;

import pub.codex.apix.schema.ApiDescription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Api 文档结构
 */
public class ApiDocument implements Comparable<ApiDocument> {

    private final String summary;

    private final String path;

    private final String methodName;

    private List<Map<String, Object>> operations;

    public ApiDocument(ApiDescription apiDescription) {

        this.summary = apiDescription.getSummary();
        this.path = apiDescription.getPath();
        this.methodName = apiDescription.getMethodName();

        if (apiDescription.getOperations().size() > 0) {
            operations = apiDescription.getOperations().stream().map(operation -> {
                Map<String, Object> map = new HashMap<>();
                map.put("summary", operation.getSummary());
                map.put("path", operation.getPath());
                map.put("method", operation.getMethod().toString());
                map.put("params", operation.getParams());
                map.put("paramsBody", operation.getParamsBody());
                return map;
            }).collect(Collectors.toList());
        }
    }


    public String getSummary() {
        return summary;
    }

    public String getPath() {
        return path;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Map<String, Object>> getOperations() {
        return operations;
    }

    @Override
    public int compareTo(ApiDocument o) {
        return summary.compareTo(o.getSummary());
    }
}
