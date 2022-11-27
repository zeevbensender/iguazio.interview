package com.lightricks.homework.crawler.model;

import org.springframework.lang.NonNull;

public class PageMessage {
    private final String url;
    private final String parentUrl;
    private final int level;

    private boolean processChildren = true;

    public PageMessage(@NonNull String url, String parentUrl, int level) {
        this.url = url;
        this.level = level;
        this.parentUrl = parentUrl;
    }

    public PageMessage(@NonNull String url, int level) {
        this.url = url;
        this.level = level;
        this.parentUrl = null;
    }

    public String getUrl() {
        return url;
    }

    public int getLevel() {
        return level;
    }

    public boolean isProcessChildren() {
        return processChildren;
    }

    public void setProcessChildren(boolean processChildren) {
        this.processChildren = processChildren;
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
}
