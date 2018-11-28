package org.codex.apix.scan;

import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.codex.apix.builder.DocumentationBuilder;
import org.codex.apix.context.DocumentationContext;
import org.codex.apix.context.RequestMappingContext;
import org.codex.apix.module.ResourceGroup;
import org.codex.apix.schema.ApiListing;
import org.codex.apix.schema.Documentation;

import java.util.List;
import java.util.Map;

/**
 * 文档扫描类
 */
@Component
public class ApiDocumentationScanner {


    private ApiListingReferenceScanner apiListingReferenceScanner;

    private ApiListingScanner apiListingScanner;

    @Autowired
    public ApiDocumentationScanner(ApiListingReferenceScanner apiListingReferenceScanner, ApiListingScanner apiListingScanner) {
        this.apiListingReferenceScanner = apiListingReferenceScanner;
        this.apiListingScanner = apiListingScanner;
    }

    public Documentation scan(DocumentationContext documentationContext) {


        // 获取所有API信息，并准备分组
        Map<ResourceGroup, List<RequestMappingContext>> resourceGroupListMap = apiListingReferenceScanner.scan(documentationContext);

        Multimap<String, ApiListing> apiListings = apiListingScanner.scan(resourceGroupListMap);


        DocumentationBuilder builder = DocumentationBuilder.getBuild();
        builder
                .setApiListings(apiListings);

        return builder.build();

    }


}
