package pub.codex.apix.doc;

import pub.codex.apix.schema.ApiDescription;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerDocument {


    private String name;

    private List<ApiDocument> apis;


    public ControllerDocument(String name, Set<ApiDescription> apis) {

        this.name = name;

        if (apis.size() > 0) {
            this.apis = apis.stream().map(ApiDocument::new)
                    .sorted().collect(Collectors.toList());
        }
    }


    public List<ApiDocument> getApis() {
        return apis;
    }

    public String getName() {
        return name;
    }
}
