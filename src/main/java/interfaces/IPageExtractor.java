package interfaces;

/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * This class simply instructs that each PageExtractor should handle how it intends to extract the pages
 */
public interface IPageExtractor {

    void handleExtraction(Page page);
}
