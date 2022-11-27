package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.queue.InputQueue;
import com.lightricks.homework.crawler.service.PageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if(!pageMessage.isProcessChildren()) {
            return;
        }
        pageReader.readPage(pageMessage.getUrl());
        while (pageReader.hasNext()) {
                PageMessage child = new PageMessage(pageReader.readLink(),
                        pageMessage.getUrl(),
                        pageMessage.getLevel() + 1);
            input.offer(child);
        }
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
