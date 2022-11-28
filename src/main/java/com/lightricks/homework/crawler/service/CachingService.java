package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.model.PageNode;
import com.lightricks.homework.crawler.service.plugins.AggregatorPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CachingService {

    private static final Logger LOG = LoggerFactory.getLogger(CachingService.class);
    private Map<String, PageNode> map = new HashMap<>();

    private PageNode root = null;
    @Autowired
    protected List<AggregatorPlugin> plugins;

    public CachingService() {

    }

    public int size() {
        return map.size();
    }

    public PageNode get(String key) {
        return map.get(key);
    }

    public Collection<PageNode> values() {
        return map.values();
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public void clear() {
        map.clear();
    }


    public void cacheLink(PageMessage pageMessage) {
        if(pageMessage.isPoisoned()) {
            for (AggregatorPlugin plugin : plugins) {
                plugin.aggregate(map);
            }
            return;
        }
        LOG.info("About to cache {}", pageMessage);
        cache(pageMessage);
    }

    private void cache(PageMessage page) {
        PageNode node = PageNode.create(page.getUrl(), page.getLevel());
        if (map.size() == 0) { //if the map is empty, it's a root node. parent must be empty
            if (page.getParentUrl() != null) {
                throw new IllegalStateException("Root page must have no parent");
            }
            root = node;
        } else { //if the map is not empty, therefore it cannot be root. a parent must be not null
            if (page.getParentUrl() == null) {
                throw new IllegalStateException("Parent must not be null");
            }
            PageNode parent = map.get(page.getParentUrl());
            if (parent == null) { //if it's not a root node, the parent must be cached
                throw new IllegalStateException("Parent is missing: " + page.getParentUrl());
            }
            parent.addChild(node);
        }
        map.put(page.getUrl(), node);
    }
}
