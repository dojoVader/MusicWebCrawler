package com.ng.jaguda.extractors;

import com.google.common.io.Files;
import com.ng.jaguda.Utils;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import com.ng.jaguda.interfaces.IPageExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */

/**
 * @description TooExclusiveExtractor is simply a class responsible for handling how it extracts MP3
 * music from tooexclusive sites
 */
public class TooExclusiveExtractor implements IPageExtractor {

    protected static final Logger logger = LoggerFactory.getLogger(TooExclusiveExtractor.class);

    private URL url;



    public void handleExtraction(Page page) {
        // Instantiate our Map

        // Get the HTMLParsed Data
        HtmlParseData htmlParsedContent = (HtmlParseData) page.getParseData();
        String htmlSection = htmlParsedContent.getHtml();
        // Fetch All the MP3 from anchor tags
        Pattern anchorTagRegEXP = Pattern.compile("<a\\shref=\\\"(.+mp3)\\\">");
        Matcher matcher = anchorTagRegEXP.matcher(htmlSection);

        while (matcher.find()) {
            String remoteMp3 = matcher.group(1);
            String song = Utils.getProperSongDestinationName("tooxclusive",remoteMp3);
            if(Utils.isExist(song)){
                logger.info("Skipping the track as it already exists {}",song);
                continue;
            }
            try {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.25.30.117",6060));
                url = new URL(remoteMp3);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
                InputStream inputStream = connection.getInputStream();
                byte[] songs = Utils.convertStreamToByteArray(inputStream);
                Files.write(songs, new File(song));
                logger.info("File Written:{}",song);

            } catch (MalformedURLException ex) {
                logger.error("Bad Url exception: {}", ex.getMessage());

            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        }


    }


}
