package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.model.PageNode;
import com.lightricks.homework.crawler.queue.InputQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Queue;

@Component
public class CrawlingService {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlingService.class);
    @Autowired
    private PageReaderService pageReader;

    @Autowired
    private InputQueue input;



    public void processPage(int levels, CachingService cache) {

        PageNode page = input.poll();
        while (!page.isPoisoned()) {
            pageReader.readPage(page.getUrl());
            while (pageReader.hasNext()) {
                PageNode child = page.addLink(pageReader.readLink());
                if(child.getLevel() < levels) {
                    input.offer(child);
                }
            }
            if(input.size() == 0) {
                input.offer(PageNode.poisonedPill());
            }
        }

        for(int i = 0; i < levels; i++) {

        }

        for (PageNode page : root.getChildren()) {
            pageReader.readPage(page.getUrl());
            while (pageReader.hasNext()) {
                page.addLink(pageReader.readLink());
            }
        }
    }
}
