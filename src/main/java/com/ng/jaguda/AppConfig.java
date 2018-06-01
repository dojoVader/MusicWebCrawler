package com.ng.jaguda;

/**
 * Created by Aderemi.Okeowo on 5/26/2018.
 */
public class AppConfig {

    private static AppConfig _instance;
    private String storageFolder;
    private String proxyHost;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;
    private String destinationMusicFolder;
    private int numOfCrawlers;
    private boolean useProxy;

    private AppConfig() {

    }

    public static AppConfig getInstance() {
        if (_instance == null) {
            _instance = new AppConfig();
        }
        return _instance;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getStorageFolder() {
        return storageFolder;
    }

    public void setStorageFolder(String storageFolder) {
        this.storageFolder = storageFolder;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public String getDestinationMusicFolder() {
        return destinationMusicFolder;
    }

    public void setDestinationMusicFolder(String destinationMusicFolder) {
        this.destinationMusicFolder = destinationMusicFolder;
    }

    public int getNumOfCrawlers() {
        return numOfCrawlers;
    }

    public void setNumOfCrawlers(int numOfCrawlers) {
        this.numOfCrawlers = numOfCrawlers;
    }
}
