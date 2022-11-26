package com.lightricks.homework.crawler.model;

import org.springframework.lang.NonNull;

public class PageNode {

    private final String url;
    private final String parentUrl;

    private boolean poisoned = false;

    private final int level;


    public PageNode(@NonNull String url, String parentUrl, int level) {
        this.url = url;
        this.level = level;
        this.parentUrl = parentUrl;
    }

    public PageNode(@NonNull String url, int level) {
        this.url = url;
        this.level = level;
        this.parentUrl = null;
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

    @Override
    public String toString() {
        return "PageNode{" +
                "url='" + url + '\'' +
                ", parentUrl='" + parentUrl + '\'' +
                ", poisoned=" + poisoned +
                ", level=" + level +
                '}';
    }

    private PageNode() {
        this.url = null;
        this.level = -1;
        poisoned = true;
        parentUrl = null;
    }

    /**
     *
     * @return poisoned PageNode to kill consumer
     */
    public static PageNode poisonedPill(){
        return new PageNode();
    }
}
