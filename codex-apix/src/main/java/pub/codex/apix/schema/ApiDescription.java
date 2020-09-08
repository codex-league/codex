package pub.codex.apix.schema;

import java.util.List;

public class ApiDescription {
    private final String summary;
    private final String path;
    private final String methodName;
    private final List<Operation> operations;

    public ApiDescription(String summary,String path, String methodName, List<Operation> operations) {
        this.summary = summary;
        this.path = path;
        this.methodName = methodName;
        this.operations = operations;
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

    public List<Operation> getOperations() {
        return operations;
    }

}
