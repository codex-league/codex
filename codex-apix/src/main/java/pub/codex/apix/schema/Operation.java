package pub.codex.apix.schema;

import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;


/**
 * mapping 属性 schema 映射
 */
public class Operation {
    private final String summary;
    private final String path;
    private final HttpMethod method;
    private List<Map<String, Object>> params;
    private Map<String, Object> paramsBody;

    public Operation(String summary, String path, HttpMethod method, List<Map<String, Object>> params, Map<String, Object> paramsBody) {
        this.summary = summary;
        this.path = path;
        this.method = method;
        this.params = params;
        this.paramsBody = paramsBody;
    }


    public String getSummary() {
        return summary;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public Map<String, Object> getParamsBody() {
        return paramsBody;
    }


}