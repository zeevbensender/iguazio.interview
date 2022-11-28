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

    private final AppContext appContext;

    public PageProcessor(@Autowired InputQueue inputQueue, @Autowired CachingService cache, @Autowired AppContext appContext) {
        this.input = inputQueue;
        this.cache = cache;
        this.appContext = appContext;
    }

    public void processPage() {
        while (input.size() != 0) {
            PageMessage page = this.input.poll();
            //Do not process children if max level reached
            if (page.getLevel() > appContext.getMaxLevel()) {
                page.setAsLeaf();
            }
            //Process the page only if wasn't processed before
            if (!cache.contains(page.getUrl())) {
                for (PageProcessingPlugin plugin : plugins) {
                    plugin.process(page);
                }
            }
        }
        //Signal last page if no more pages in the queue
        for (PageProcessingPlugin plugin : plugins) {
            plugin.process(PageMessage.poisonedPill());
        }

    }
}