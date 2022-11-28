package com.lightricks.homework.crawler.model;

public class PageMessage {
    private final String url;
    private final String parentUrl;
    private final int level;

    private final boolean lastLinkOnPage;

    private boolean leaf = false;

    public PageMessage(String url, String parentUrl, int level, boolean lastLinkOnPage) {
        this.url = url;
        this.level = level;
        this.parentUrl = parentUrl;
        this.lastLinkOnPage = lastLinkOnPage;
    }

    public PageMessage(String url, int level) {
        this.url = url;
        this.level = level;
        this.parentUrl = null;
        this.lastLinkOnPage = false;
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

    public boolean isLastLinkOnPage() {
        return lastLinkOnPage;
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
        return new PageMessage(null, null, -1, true);
    }
}
