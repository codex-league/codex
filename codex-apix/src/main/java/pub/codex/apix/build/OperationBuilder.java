package pub.codex.apix.build;

import org.springframework.http.HttpMethod;
import pub.codex.apix.schema.Operation;

import java.util.List;
import java.util.Map;

/**
 * api 选项内容构造器
 */
public class OperationBuilder {

    private String summary;
    private String path;
    private HttpMethod method = HttpMethod.GET;
    private List<Map<String, Object>> params;
    private Map<String, Object> paramsBody;
    private List<Map<String, Object>>  responseBody;

    public OperationBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public OperationBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public OperationBuilder setParams(List<Map<String, Object>> params) {
        this.params = params;
        return this;
    }

    public OperationBuilder setParamsBody(Map<String, Object> paramsBody) {
        this.paramsBody = paramsBody;
        return this;
    }

    public OperationBuilder setResponseBody(List<Map<String, Object>> responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public Operation build() {
        return new Operation(summary, path, method, params, paramsBody, responseBody);
    }
}