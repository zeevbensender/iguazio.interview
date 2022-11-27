package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.utils.UrlUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Queue;

@Service
@Scope("prototype")
public class JSoupPageReader implements PageReader {
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
            if (!UrlUtils.isDomain(child) ||
                    child.endsWith(".pdf") ||
                    child.endsWith(".PDF") ||
                    child.endsWith(".jpg") ||
                    child.endsWith(".JPG") ||
                    child.endsWith(".gif") ||
                    child.endsWith(".GIF") ||
                    child.endsWith(".xml") ||
                    child.endsWith(".XML")  //content types other than html will be filtered out later.
                // this condition is to avoid annoying error logs
            ) {
                continue;
            }
            children.offer(child);
        }
    }

    public String readLink() {
        return children.poll();
    }

    @Override
    public boolean hasNext() {
        return !children.isEmpty();
    }
}
