package pub.codex.apix.schema;

import java.util.List;
import java.util.Set;

public class ApiListing {

    private final Set<ApiDescription> apis;


    public ApiListing(Set<ApiDescription> apis) {
        this.apis = apis;

    }


    public Set<ApiDescription> getApis() {
        return apis;
    }


}
