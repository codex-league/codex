package pub.codex.apix.build;

import pub.codex.apix.schema.ApiDescription;
import pub.codex.apix.schema.Operation;

import java.util.List;


/**
 * API描述信息构建
 */
public class ApiDescriptionBuilder {

    private String summary;
    private String path;
    private String methodName;
    private List<Operation> operations;

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ApiDescriptionBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public ApiDescriptionBuilder setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public ApiDescriptionBuilder setOperations(List<Operation> operations) {
        this.operations = operations;
        return this;
    }

    public ApiDescription build() {
        return new ApiDescription(summary, path, methodName, operations);
    }
}
