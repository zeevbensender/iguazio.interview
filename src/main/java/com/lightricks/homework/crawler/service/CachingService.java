package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.model.PageMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope("prototype")
public class CachingService {

    private static final Logger LOG = LoggerFactory.getLogger(CachingService.class);
    private Map<String, PageMessage> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public PageMessage get(Object key) {
        return map.get(key);
    }

    public Collection<PageMessage> values() {
        return map.values();
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public void clear() {
        map.clear();
    }



    public void addLink(PageMessage page) {
        LOG.info("{}", page);
        map.put(page.getUrl(), page);

    }

}
