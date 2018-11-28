package org.codex.apix.builder;

import org.codex.apix.schema.ApiDescription;
import org.codex.apix.schema.Operation;

import java.util.List;


/**
 * API描述信息构建
 */
public class ApiDescriptionBuilder {

    private String path;
    private String methodName;
    private List<Operation> operations;


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
        return new ApiDescription(path, methodName, operations);
    }
}
