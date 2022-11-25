package com.lightricks.homework.crawler.service;

import jakarta.annotation.PostConstruct;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Crawler {
    private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);

    public Document getDocument(String url) {
        Document doc = null;
        try {
            Connection con = Jsoup.connect(url);
            doc = con.get();
        }catch (IOException e) {
            LOG.error("Failed to get {} URL. Error: {}", url, e.getMessage());
        }
        return doc;
    }

    public void parseDocument(String url) {
        Document doc = getDocument(url);
        if(doc == null) {
            return;
        }
        for(Element link : doc.select("a[href]")) {
            LOG.info(link.absUrl("href"));
        }
    }
}
