package com.ng.jaguda;

import com.ng.jaguda.exceptions.ApplicationConfigException;
import com.ng.jaguda.extractors.NotJustOkExtractor;
import com.ng.jaguda.extractors.PageExtractorRegistry;
import com.ng.jaguda.extractors.TooExclusiveExtractor;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */
public class JagudaController {

    protected static final Logger logger = LoggerFactory.getLogger(JagudaController.class);

    public static void main(String[] args) throws Exception {


        try {
            // Get the application config
            String pathConfig = "./appconfig.xml";
            FileInputStream fileInputStream = new FileInputStream(pathConfig);
            Properties properties = new Properties();
            properties.loadFromXML(fileInputStream);
            fileInputStream.close(); // Close File Connection
            AppConfig appConfig = Utils.getConfigurator(properties);

            if (appConfig.getStorageFolder() == null && appConfig.getDestinationMusicFolder() == null) {
                throw new ApplicationConfigException("Application Configuration must be set to run this application");
            }

            CrawlConfig config = new CrawlConfig();
            config.setIncludeHttpsPages(true);
            if (appConfig.isUseProxy()) {
                config.setProxyHost(appConfig.getProxyHost());
                config.setProxyPort(appConfig.getProxyPort());
                config.setProxyUsername(appConfig.getProxyUsername());
                config.setProxyPassword(appConfig.getProxyPassword());
            }


            //Store the media if found
            config.setCrawlStorageFolder(appConfig.getStorageFolder());
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
            PageExtractorRegistry.registerExtractorPerHost("tooxclusive.com", new TooExclusiveExtractor());
            crawlController.start(JagudaCrawler.class, appConfig.getNumOfCrawlers());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (ApplicationConfigException e) {
            logger.error(e.getMessage());
        }


    }
}
