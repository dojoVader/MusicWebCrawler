package com.ng.jaguda.extractors;

import com.google.common.io.Files;
import com.ng.jaguda.AppConfig;
import com.ng.jaguda.Utils;
import com.ng.jaguda.interfaces.IPageExtractor;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
public class NotJustOkExtractor implements IPageExtractor {

    protected static final Logger logger = LoggerFactory.getLogger(NotJustOkExtractor.class);
    private URL url;

    public void handleExtraction(Page page) {
        // Instantiate our Map

        // Get the HTMLParsed Data
        HtmlParseData htmlParsedContent = (HtmlParseData) page.getParseData();
        String htmlSection = htmlParsedContent.getHtml();
        // Fetch All the MP3 from anchor tags
        Pattern jsonPlayListData = Pattern.compile("<script\\stype=\\\"application\\/json\\\"\\sclass=\\\"cue-playlist-data\\\">(.+)<\\/script>");
        Matcher matcher = jsonPlayListData.matcher(htmlSection);

        while (matcher.find()) {
            // We have found the JSON PlayList in the HTML
            String jsonText = matcher.group(1);
            // Let's parse the JSON Text to an Object
            try {
                JSONObject jsonMP3PlayList = new JSONObject(jsonText);
                //Fetch the tracks as they are in an array called tracks
                JSONArray trackArrayList = jsonMP3PlayList.getJSONArray("tracks");
                for (int index = 0; index < trackArrayList.length(); index++) {
                    //Get the entry for each SONG
                    JSONObject trackData = trackArrayList.getJSONObject(index);
                    String songData = trackData.getString("mp3");
                    String song = Utils.getProperSongDestinationName("notjustok", songData);
                    if (Utils.isExist(song)) {
                        logger.info("Skipping the track as it already exists {}", song);
                        continue;
                    }
                    try {
                        AppConfig config = AppConfig.getInstance();
                        byte[] songs;
                        if (config.isUseProxy()) {
                            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(config.getProxyHost(), config.getProxyPort()));
                            url = new URL(songData);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
                            InputStream inputStream = connection.getInputStream();
                            songs = Utils.convertStreamToByteArray(inputStream);
                        } else {
                            url = new URL(songData);
                            InputStream inputStream = url.openStream();
                            songs = Utils.convertStreamToByteArray(inputStream);

                        }



                        Files.write(songs, new File(song));
                        logger.info("File Written:{}", song);

                    } catch (MalformedURLException ex) {
                        logger.error("Bad Url exception: {}", ex.getMessage());

                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }

            } catch (JSONException e) {
                logger.error("Error parsing JSON cause: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Something else must have happened: {}", e.getMessage());
            }


        }
    }

}
