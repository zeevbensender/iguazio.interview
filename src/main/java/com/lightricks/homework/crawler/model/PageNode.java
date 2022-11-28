package com.lightricks.homework.crawler.model;

import com.lightricks.homework.crawler.utils.UrlUtils;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Cache node. Each node represents a single link.
 * Cached within {@link com.lightricks.homework.crawler.service.CachingService}
 */
public class PageNode {
    private final String url;
    private final int level;
    private final Set<PageNode> children = new HashSet<>();

    private final String domain;

    private int selfDomainCount = 0;


    private PageNode(String url, int level, String domain) {
        this.url = url;
        this.level = level;
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public int getLevel() {
        return level;
    }

    public float getSelfDomainCount() {
        return selfDomainCount;
    }

    public float getChildrenCount() {
        return children.size();
    }

    public boolean addChild(PageNode child) {
        if(this.domain.equals(child.domain)) {
            selfDomainCount++;
        }
        return children.add(child);
    }

    public Stream<PageNode> childrenStream() {
        return children.stream();
    }

    public static PageNode create(String url, int level) {
        try {
            return new PageNode(url, level, UrlUtils.getDomain(url));
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Malformed URL: " + url, e);
        }
    }
}
