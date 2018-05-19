package extractors;



import interfaces.IPageExtractor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */
public class PageExtractorRegistry {

    static Map<String, Object> map = new HashMap<>();

    public static void registerExtractorPerHost(String hostname, IPageExtractor extractor) {
        map.put(hostname, extractor);
    }

    public static IPageExtractor getExtractor(String key) {
        IPageExtractor pageExtractor = (IPageExtractor) map.get(key);
        if (pageExtractor == null) {
            return new BasicPageExtractor();
        } else {
            return pageExtractor;
        }

    }

}
