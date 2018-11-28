package org.codex.apix.builder;

import com.google.common.collect.Multimap;
import org.codex.apix.schema.ApiListing;
import org.codex.apix.schema.Documentation;

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
