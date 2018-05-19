import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import extractors.NotJustOkExtractor;
import extractors.PageExtractorRegistry;
import extractors.TooExclusiveExtractor;

/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */
public class JagudaController {

    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/songs";
        int numberOfCrawlers = 5;
        CrawlConfig config = new CrawlConfig();
        config.setIncludeHttpsPages(true);
        config.setProxyHost("172.25.30.117");
        config.setProxyPort(6060);
        config.setProxyUsername("hudson");
        config.setProxyPassword("password");
        //Store the media if found
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setIncludeBinaryContentInCrawling(true);
        config.setResumableCrawling(true);
        config.setMaxDownloadSize((20 * 1024 * 1024));

        // Instantiate the controller
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController crawlController = new CrawlController(config, pageFetcher, robotstxtServer);

        crawlController.addSeed("https://notjustok.com/category/download-mp3/");
        crawlController.addSeed("http://tooxclusive.com/artists-z-music-list/");

        PageExtractorRegistry.registerExtractorPerHost("notjustok.com", new NotJustOkExtractor());
        PageExtractorRegistry.registerExtractorPerHost("tooexclusive.com", new TooExclusiveExtractor());
        crawlController.start(JagudaCrawler.class, numberOfCrawlers);


    }
}
