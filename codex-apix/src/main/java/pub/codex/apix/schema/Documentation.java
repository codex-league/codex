package pub.codex.apix.schema;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Documentation {

    private final Multimap<String, ApiListing> apiListings;

    public Documentation(Multimap<String, ApiListing> apiListings) {
        this.apiListings = apiListings;
    }

    public Multimap<String, ApiListing> getApiListings() {

        // 将Multimap转换为List
        List<Map.Entry<String, ApiListing>> list = Lists.newArrayList(apiListings.entries());

        // 对List进行排序
        list.sort(Comparator.comparing(Map.Entry::getKey));

        // 将排序后的List转换回Multimap
        Multimap<String, ApiListing> sortedMultimap = ArrayListMultimap.create();
        list.forEach(entry -> sortedMultimap.put(entry.getKey(), entry.getValue()));

        return sortedMultimap;
    }
}