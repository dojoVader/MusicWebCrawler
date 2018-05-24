package com.ng.jaguda.extractors;

import com.google.common.io.Files;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import com.ng.jaguda.interfaces.IPageExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */
public class BasicPageExtractor implements IPageExtractor {

    protected static final Logger logger = LoggerFactory.getLogger(BasicPageExtractor.class);

    @Override
    public void handleExtraction(Page page) {

    }
}
