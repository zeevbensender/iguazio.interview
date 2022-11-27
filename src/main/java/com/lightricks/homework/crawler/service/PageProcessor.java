package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.queue.InputQueue;
import com.lightricks.homework.crawler.service.plugins.PageProcessingPlugin;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TreeSet;

@Component
public class PageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(PageProcessor.class);
    @Autowired
    private final Set<PageProcessingPlugin> plugins = new TreeSet<>();
    private final InputQueue input;
    private final CachingService cache;

    public PageProcessor(@Autowired InputQueue inputQueue, @Autowired CachingService cache) {
        this.input = inputQueue;
        this.cache = cache;
    }

    @PostConstruct
    public void init() {
        this.processPage(1);
    }

    public void processPage(int maxLevel) {
        while (input.size() != 0) {
            try {
                PageMessage page = this.input.poll();
                if(page.getLevel() == maxLevel) {
                    page.doNotProcessChildren();
                }
                if (!cache.contains(page.getUrl())) {
                    for (PageProcessingPlugin plugin : plugins) {
                        plugin.process(page);
                    }
                }
            } catch (InterruptedException e) {
                LOG.warn("Interrupted: {}", e);
            }
        }
    }
}