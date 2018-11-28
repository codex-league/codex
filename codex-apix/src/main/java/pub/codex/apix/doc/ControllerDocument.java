package pub.codex.apix.doc;

import pub.codex.apix.schema.ApiDescription;

import java.util.ArrayList;
import java.util.List;

public class ControllerDocument {


    private String name;

    private List<ApiDocument> apis;


    public ControllerDocument(String name, List<ApiDescription> apis) {

        this.name = name;

        if (apis.size() > 0) {
            this.apis = new ArrayList<>();
            apis.forEach(apiDescription -> {
                this.apis.add(new ApiDocument(apiDescription));
            });
        }
    }


    public List<ApiDocument> getApis() {
        return apis;
    }

    public String getName() {
        return name;
    }
}
