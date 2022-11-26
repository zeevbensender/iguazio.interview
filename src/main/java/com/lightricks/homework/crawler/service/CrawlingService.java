package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.model.PageNode;
import com.lightricks.homework.crawler.queue.InputQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrawlingService {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlingService.class);
    private final PageReaderService pageReader;

    private final InputQueue input;

    private final CachingService cache;

    public CrawlingService(@Autowired PageReaderService pageReader, @Autowired InputQueue input, @Autowired CachingService cache) {
        this.pageReader = pageReader;
        this.input = input;
        this.cache = cache;
    }


    public void processPage(int levels) {


        while (true) {
            try {
                PageNode page = this.input.take();
                LOG.error("{}", page);
                if (page.isPoisoned()) {
                    break;
                }
                cache.addLink(page);
                pageReader.readPage(page.getUrl());
                while (pageReader.hasNext()) {
                    PageNode child = new PageNode(pageReader.readLink(), page.getUrl(), page.getLevel() + 1);
                    if (child.getLevel() <= levels) {
                        input.offer(child);
                    }
                }
                if (input.size() == 0) {
                    input.offer(PageNode.poisonedPill());
                }
            }catch (InterruptedException e) {
                LOG.warn("Interrupted: {}" , e);
            }
        }

    }
}
