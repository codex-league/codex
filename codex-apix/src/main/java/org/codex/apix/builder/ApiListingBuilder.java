package org.codex.apix.builder;

import org.codex.apix.schema.ApiDescription;
import org.codex.apix.schema.ApiListing;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ApiListingBuilder {

    private List<ApiDescription> apis = newArrayList();


    public static ApiListingBuilder getBuild() {
        return new ApiListingBuilder();
    }

    public ApiListingBuilder apis(List<ApiDescription> apis) {
        if (apis != null) {
            this.apis = apis;
        }
        return this;
    }



    public ApiListing build() {
        return new ApiListing(apis);
    }
}
