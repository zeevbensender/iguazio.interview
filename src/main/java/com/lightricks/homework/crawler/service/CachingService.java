package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.model.PageNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope("prototype")
public class CachingService {

    private static final Logger LOG = LoggerFactory.getLogger(CachingService.class);
    private Map<String, PageNode> map = new HashMap<>();

    private PageNode root = null;
    private final ScoreProcessor scoreProcessor;

    public CachingService(@Autowired ScoreProcessor scoreProcessor) {
        this.scoreProcessor = scoreProcessor;
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


    public void addLink(PageMessage page) {
        LOG.info("{}", page);
        PageNode node = new PageNode(page.getUrl(), page.getLevel());
        if (map.size() == 0) {
            if (page.getParentUrl() != null) {
                throw new IllegalStateException("Root page must have no parent");
            }
            root = node;
        } else {
            if (page.getParentUrl() == null) {
                throw new IllegalStateException("Parent must not be null");
            }
            PageNode parent = map.get(page.getParentUrl());
            if(parent == null) {
                throw new IllegalStateException("Parent is missing: " + page.getParentUrl());
            }
            parent.addChild(node);
        }
        map.put(page.getUrl(), node);
        if(page.isLastLinkOnPage()) {
            scoreProcessor.processPage(map.get(page.getParentUrl()));
        }
    }
}
