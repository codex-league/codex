package pub.codex.apix.build;

import com.google.common.collect.Multimap;
import pub.codex.apix.schema.ApiListing;
import pub.codex.apix.schema.Documentation;

public class DocumentationBuilder {

    private Multimap<String, ApiListing> apiListings;

    public DocumentationBuilder setApiListings(Multimap<String, ApiListing> apiListings) {
        this.apiListings = apiListings;
        return this;
    }

    public static DocumentationBuilder getBuild(){
        return new DocumentationBuilder();
    }

    public Documentation build() {
        return new Documentation(
                apiListings);
    }



}
