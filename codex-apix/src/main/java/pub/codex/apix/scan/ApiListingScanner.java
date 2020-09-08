package pub.codex.apix.scan;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.codex.apix.build.ApiListingBuilder;
import pub.codex.apix.context.DescriptionContext;
import pub.codex.apix.module.ResourceGroup;
import pub.codex.apix.schema.ApiDescription;
import pub.codex.apix.schema.ApiListing;

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


    public Multimap<String, ApiListing> scan(Map<ResourceGroup, List<DescriptionContext>> resourceGroupListMap) {

        Multimap<String, ApiListing> apiListingMap = LinkedListMultimap.create();

        for (ResourceGroup resourceGroup : resourceGroupListMap.keySet()) {

            Set<ApiDescription> apiDescriptions = newHashSet();

            for (DescriptionContext each : resourceGroupListMap.get(resourceGroup)) {

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
