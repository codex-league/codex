package org.codex.apix.schema;

import java.util.List;

public class ApiDescription {
    private final String path;
    private final String methodName;
    private final List<Operation> operations;

    public ApiDescription(String path, String methodName, List<Operation> operations) {
        this.path = path;
        this.methodName = methodName;
        this.operations = operations;
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
