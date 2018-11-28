package org.codex.apix.scan;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.codex.apix.builder.ApiListingBuilder;
import org.codex.apix.context.RequestMappingContext;
import org.codex.apix.module.ResourceGroup;
import org.codex.apix.schema.ApiDescription;
import org.codex.apix.schema.ApiListing;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

@Component
public class ApiListingScanner {


    private ApiDescriptionReader apiDescriptionReader;

    @Autowired
    public ApiListingScanner(ApiDescriptionReader apiDescriptionReader) {
        this.apiDescriptionReader = apiDescriptionReader;
    }



    public Multimap<String, ApiListing> scan(Map<ResourceGroup, List<RequestMappingContext>> resourceGroupListMap) {

        Multimap<String, ApiListing> apiListingMap = LinkedListMultimap.create();

        for (ResourceGroup resourceGroup : resourceGroupListMap.keySet()) {

            Set<ApiDescription> apiDescriptions = newHashSet();

            for (RequestMappingContext each : resourceGroupListMap.get(resourceGroup)) {

                apiDescriptions.addAll(apiDescriptionReader.read(each));
            }
            List<ApiDescription> sortedApis = newArrayList(apiDescriptions);

            ApiListingBuilder apiListingBuilder = ApiListingBuilder.getBuild()
                    .apis(sortedApis);

            apiListingMap.put(resourceGroup.getGroupName(), apiListingBuilder.build());

        }

        return apiListingMap;
    }
}
