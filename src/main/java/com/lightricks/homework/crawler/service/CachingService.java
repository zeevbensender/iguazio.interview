package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.model.PageNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope("prototype")
public class CachingService {
    private Map<String, PageNode> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public PageNode get(Object key) {
        return map.get(key);
    }

    public Collection<PageNode> values() {
        return map.values();
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }



    public void addLink(PageNode page) {
        map.put(page.getUrl(), page);
    }

}
