package com.lightricks.homework.crawler.service.plugin.processing;

import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.queue.InputQueue;
import com.lightricks.homework.crawler.service.reader.PageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Reads links from {@link PageReader} and sends them to {@link com.lightricks.homework.crawler.service.PageProcessor }
 * over {@link InputQueue}
 */
@Service
public class CrawlingPlugin implements PageProcessingPlugin {
    private final PageReader pageReader;
    private final InputQueue input;

    public CrawlingPlugin(@Autowired PageReader pageReader, @Autowired InputQueue input) {
        this.pageReader = pageReader;
        this.input = input;
    }

    @Override
    public void process(PageMessage pageMessage) {
        //do nothing if it's the last input signal message or if it's a deepest level message
        if (pageMessage.isLeaf() || pageMessage.isPoisoned()) {
            return;
        }
        //read links on page
        pageReader.readPage(pageMessage.getUrl());
        //get page links one by one
        while (pageReader.hasNext()) {
            //send the links to PageProcessor
            String link = pageReader.readLink();
            PageMessage child = new PageMessage(link,
                    pageMessage.getUrl(),
                    pageMessage.getLevel() + 1);
            input.offer(child);
        }
    }

    /**
     *
     * @return running order
     */
    @Override
    public int getPriority() {
        return 2;
    }
}
