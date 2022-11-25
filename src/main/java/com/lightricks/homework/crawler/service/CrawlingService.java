package com.lightricks.homework.crawler.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

@Component
public class CrawlingService {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlingService.class);
    @Autowired
    PageReaderService pageReader;



    public Queue<String> processPage(Queue<String> links, CachingService cache) {
        Queue<String> children = new ArrayDeque<>();
        while (! links.isEmpty()) {
            String link = links.poll();
            pageReader.readPage(link);
            while (pageReader.hasNext()) {
                children.offer(pageReader.readLink());
            }
        }
        return children;
    }
}
