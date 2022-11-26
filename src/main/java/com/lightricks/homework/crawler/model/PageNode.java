package com.lightricks.homework.crawler.model;

import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PageNode {

    private final String url;

    private boolean poisoned = false;

    private final int level;
    private final Set<PageNode> children = new HashSet<>();

    public PageNode(@NonNull String url, int level) {
        this.url = url;
        this.level = level;
    }

    /**
     * Creates a new child PageNode with the next level
     *
     * @param link Appearing on the current page
     * @return child node if it does not exist yet, null otherwise
     */
    public PageNode addLink(@NonNull String link) {
        PageNode child = new PageNode(link, level + 1);
        return children.add(child) ? child : null;
    }

    public Collection<PageNode> getChildren() {
        return children;
    }

    public String getUrl() {
        return url;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageNode pageNode = (PageNode) o;

        return url.equals(pageNode.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    private PageNode() {
        this.url = null;
        this.level = -1;
        poisoned = true;
    }

    /**
     *
     * @return poisoned PageNode to kill consumer
     */
    public static PageNode poisonedPill(){
        return new PageNode();
    }
}
