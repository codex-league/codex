package pub.codex.apix.schema;

import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;


/**
 * mapping 属性 schema 映射
 */
public class Operation {
    private final HttpMethod method;
    private final String summary;
    private List<Map<String, Object>> params;
    private Map<String, Object> paramsBody;

    public Operation(HttpMethod method, String summary, List<Map<String, Object>> params, Map<String, Object> paramsBody) {
        this.method = method;
        this.summary = summary;
        this.params = params;
        this.paramsBody = paramsBody;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getSummary() {
        return summary;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public Map<String, Object> getParamsBody() {
        return paramsBody;
    }
}