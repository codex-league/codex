package org.codex.apix.schema;

import com.google.common.collect.Multimap;

public class Documentation {

    private final Multimap<String, ApiListing> apiListings;

    public Documentation(Multimap<String, ApiListing> apiListings) {
        this.apiListings = apiListings;
    }

    public Multimap<String, ApiListing> getApiListings() {
        return apiListings;
    }
}