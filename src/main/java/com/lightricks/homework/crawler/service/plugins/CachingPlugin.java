package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.service.CachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CachingPlugin implements PageProcessingPlugin{

    private final CachingService cache;

    public CachingPlugin(@Autowired CachingService cache) {
        this.cache = cache;
    }

    @Override
    public void process(PageMessage pageMessage) {
        cache.cacheLink(pageMessage);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
