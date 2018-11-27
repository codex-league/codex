package org.codex.apix.schema;

import java.util.List;

public class ApiListing {

    private final List<ApiDescription> apis;


    public ApiListing(List<ApiDescription> apis) {
        this.apis = apis;

    }


    public List<ApiDescription> getApis() {
        return apis;
    }


}
