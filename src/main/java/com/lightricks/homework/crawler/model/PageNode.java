package com.lightricks.homework.crawler.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class PageNode {
    private final String url;
    private final int level;
    private final Set<PageNode> children = new HashSet<>();


    public PageNode(String url, int level) {
        this.url = url;
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public int getLevel() {
        return level;
    }

    public boolean addChild(PageNode child) {
        return children.add(child);
    }

    public Stream<PageNode> childrenStream() {
        return children.stream();
    }
}
