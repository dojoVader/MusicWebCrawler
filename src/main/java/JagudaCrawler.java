/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */

import com.google.common.io.Files;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import extractors.PageExtractorRegistry;
import interfaces.IPageExtractor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

/**
 * @description The JagudaCrawler Class that will crawl the sites
 */
public class JagudaCrawler extends WebCrawler {

    private final static String[] allowedSites = {"https://notjustok.com/", "http://tooxclusive.com"};
    private static String crawledExtensions = ".*(\\.(ogg|mp3|wma|m4a))$";
    private final static Pattern FILTERS = Pattern.compile(crawledExtensions);

    /**
     * at this point we only want to visit Pages that are related to our domain and not external third parties site
     * we have no interest in, e.g Scripts, Ads etc
     *
     * @param referringPage
     * @param url
     * @return
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        // Get the Url of the current Page
        String currentHref = url.getURL().toLowerCase();

        //Strategy: Only Allow if it comes from the crawled site
        if (matchesSite(currentHref)) {
            // Strategy: Only allow if it comes from the site and is the not an extension File
            if (currentHref.endsWith(".htm") || currentHref.endsWith(".html")) {
                return true; //
            }
            if (Utils.hasExtension(currentHref)) {
                if (Utils.isMusicExtension(currentHref)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                //It means it's a Url so let's visit
                return true;
            }
        }
        return false;
    }

    // Check that it matches one of our allowedSites
    private boolean matchesSite(String href) {
        for (String site : allowedSites) {
            if (href.startsWith(site)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        String pageUrl = page.getWebURL().getURL().toLowerCase();

        if (FILTERS.matcher(pageUrl).matches()) {
            // We want the mp3 not a parse html Data
            //Let's get the name of the file
            String[] name = pageUrl.split("/");
            String lastName = name[name.length - 1];
            String destinationPath = "/data/mp3/" + lastName;
            try {
                Files.write(page.getContentData(), new File(destinationPath));
                logger.info("File {} has been written to path {}", lastName, destinationPath);
            } catch (IOException ex) {

                logger.error(ex.getMessage());
            }


        } else {
            try {
                URI Hostname = new URI(pageUrl);
                String PageHostName = Hostname.getHost();
                IPageExtractor extractor = PageExtractorRegistry.getExtractor(PageHostName);
                extractor.handleExtraction(page);
            } catch (URISyntaxException ex) {
                logger.error("Failed to start URI, bad syntax {}", pageUrl);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
