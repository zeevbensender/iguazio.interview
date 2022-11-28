package com.lightricks.homework.crawler.service.reader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * This class will be in use only if the application runs with "jsoup" profile
 * By default {@link DefaultPageReader} is used
 */
@Service
@Profile("jsoup")
@Primary
public class JSoupPageReader extends AbstractPageReader {
    private Queue<String> children;
    private static final Logger LOG = LoggerFactory.getLogger(JSoupPageReader.class);

    private Document getPage(String url) {
        Document doc = null;
        try {
            Connection con = Jsoup.connect(url).
                    userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");
            Connection.Response resp = con.execute();
            if (!resp.contentType().startsWith("text/html")) {
                return null;
            } else {
                doc = con.get();
            }
        } catch (Exception e) {
            LOG.error("Failed to get {} URL. Error: {}", url, e.getMessage());
        }
        return doc;
    }

    public void readPage(String link) {
        children = new ArrayDeque<>();
        Document doc = getPage(link);
        if (doc == null) {
            return;
        }
        for (Element element : doc.select("a[href]")) {
            String child = element.absUrl("href");
            offerLink(child);
        }
    }
}
