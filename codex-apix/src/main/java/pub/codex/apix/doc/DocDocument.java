package pub.codex.apix.doc;

import pub.codex.apix.schema.ApiListing;
import pub.codex.apix.schema.Documentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 文档包装类
 */
public class DocDocument {


    private List<ControllerDocument> controller = new ArrayList<>();


    public List<ControllerDocument> getController() {
        return controller;
    }

    public void setController(List<ControllerDocument> controller) {
        this.controller = controller;
    }


    public DocDocument(Documentation documentation) {

        Set<String> apiListings = documentation.getApiListings().keySet();
        for (String apiListingsKey : apiListings) {
            Collection<ApiListing> collection = documentation.getApiListings().get(apiListingsKey);
            collection.forEach(apiListing -> {
                controller.add(new ControllerDocument(apiListingsKey, apiListing.getApis()));
            });
        }
    }
}
