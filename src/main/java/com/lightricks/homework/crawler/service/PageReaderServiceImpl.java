package com.lightricks.homework.crawler.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

@Service
@Scope("prototype")
public class PageReaderServiceImpl implements PageReaderService {
    private Queue<String> children;
    private static final Logger LOG = LoggerFactory.getLogger(PageReaderServiceImpl.class);
    private Document getPage(String url) {
        Document doc = null;
        try {
            Connection con = Jsoup.connect(url);
            doc = con.get();
        }catch (IOException e) {
            LOG.error("Failed to get {} URL. Error: {}", url, e.getMessage());
        }
        return doc;

    }
    public void readPage(String url) {
        children = new ArrayDeque<>();


        Document doc = getPage(url);
        if(doc == null) {
            return;
        }
        for(Element link : doc.select("a[href]")) {
            String child = link.absUrl("href");
            LOG.info(child);
            children.offer(child);
        }
    }

    public String readLink () {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean hasNext() {
        return children.isEmpty();
    }
}
