package pub.codex.apix.build;

import com.google.common.collect.Sets;
import pub.codex.apix.schema.ApiDescription;
import pub.codex.apix.schema.ApiListing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

public class ApiListingBuilder {

    private Set<ApiDescription> apis = Sets.newHashSet();


    public static ApiListingBuilder getBuild() {
        return new ApiListingBuilder();
    }

    public ApiListingBuilder apis(Set<ApiDescription> apis) {
        if (apis != null) {
            this.apis = apis;
        }
        return this;
    }



    public ApiListing build() {
        return new ApiListing(apis);
    }
}
