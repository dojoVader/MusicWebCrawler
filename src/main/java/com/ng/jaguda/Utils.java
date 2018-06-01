package com.ng.jaguda;

import com.ng.jaguda.exceptions.ApplicationConfigException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */
public class Utils {

    public static boolean isMusicExtension(String currentHref) {

        Pattern pattern = Pattern.compile(".*\\.(mp3|m4a|wma)$");
        return pattern.matcher(currentHref).matches();
    }

    public static boolean hasExtension(String currentHref) {
        Pattern pattern = Pattern.compile(".*\\.\\w{2,4}$");
        return pattern.matcher(currentHref).matches();
    }

    public static byte[] convertStreamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = stream.read(buffer)) > 0) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static String getProperSongDestinationName(String dirname, String name) {
        AppConfig appConfig = AppConfig.getInstance();
        String[] names = name.split("/");
        String lastName = names[names.length - 1];
        String folder = appConfig.getDestinationMusicFolder();
        //Check that the folder exists
        File file = new File(folder + "/" + dirname);
        if (!file.exists()) {
            file.mkdirs();
        }
        return String.format("%s%s/%s", folder, dirname, lastName);
    }

    public static boolean isExist(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    public static AppConfig getConfigurator(Properties properties) throws ApplicationConfigException {

        AppConfig appConfigInstance = AppConfig.getInstance();

        // Set each of the property
        String storageFolder = properties.getProperty("app.storagefolder");
        String destinationMusicFolder = properties.getProperty("app.destination_music_folder");
        int numOfCrawlers = Integer.parseInt(properties.getProperty("app.num_of_crawlers", "4"));
        boolean shouldUseProxy = Boolean.parseBoolean(properties.getProperty("app.use_proxy", "false"));


        //Set the configuration for the application
        appConfigInstance.setStorageFolder(storageFolder);
        appConfigInstance.setDestinationMusicFolder(destinationMusicFolder);
        appConfigInstance.setNumOfCrawlers(numOfCrawlers);
        if (shouldUseProxy) {
            //Set the proxy information for proxy
            String proxyHost = properties.getProperty("app.proxy");
            int proxyPort = Integer.parseInt(properties.getProperty("app.proxy_port"));
            String proxyUsername = properties.getProperty("app.proxy_username");
            String proxyPassword = properties.getProperty("app.proxy_password");

            appConfigInstance.setProxyHost(proxyHost);
            appConfigInstance.setProxyPassword(proxyPassword);
            appConfigInstance.setProxyUsername(proxyUsername);
            appConfigInstance.setProxyPassword(proxyPassword);
            appConfigInstance.setProxyPort(proxyPort);
            appConfigInstance.setUseProxy(shouldUseProxy);
        }
        return appConfigInstance;

    }


}
