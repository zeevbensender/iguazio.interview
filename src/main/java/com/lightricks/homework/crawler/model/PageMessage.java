package com.lightricks.homework.crawler.model;

/**
 * Single link (root or a link from the parent page) travels through ({@link com.lightricks.homework.crawler.queue.InputQueue} )
 * From {@link com.lightricks.homework.crawler.service.plugins.CrawlingPlugin} to {@link com.lightricks.homework.crawler.service.PageProcessor}
 */
public class PageMessage {
    private final String url;
    private final String parentUrl;
    private final int level;
    private boolean leaf = false;

    public PageMessage(String url, String parentUrl, int level) {
        this.url = url;
        this.level = level;
        this.parentUrl = parentUrl;
    }

    public PageMessage(String url, int level) {
        this.url = url;
        this.level = level;
        this.parentUrl = null;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public String getUrl() {
        return url;
    }

    public int getLevel() {
        return level;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setAsLeaf() {
        this.leaf = true;
    }

    public boolean isPoisoned() {
        return level == -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageMessage pageNode = (PageMessage) o;

        return url.equals(pageNode.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "PageNode{" +
                "url='" + url + '\'' +
                ", parentUrl='" + parentUrl + '\'' +
                ", level=" + level +
                '}';
    }

    public static PageMessage poisonedPill() {
        return new PageMessage(null, null, -1);
    }
}
