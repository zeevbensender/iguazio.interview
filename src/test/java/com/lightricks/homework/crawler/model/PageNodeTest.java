package com.lightricks.homework.crawler.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageNodeTest {
    private PageNode root;
    @BeforeEach
    void setUp() {
        root = PageNode.create("https://en.wikipedia.org/", 0);
    }

    @Test
    public void testSameDomain() {
        root.addChild(PageNode.create("https://en.wikipedia.org/wiki/Wikipedia:About", 1));
        assertEquals(1, root.getSelfDomainCount());
        assertEquals(1, root.getChildrenCount());
    }

    @Test
    public void testOtherDomain() {
        root.addChild(PageNode.create("https://foundation.wikimedia.org/wiki/Terms_of_Use", 1));
        assertEquals(0, root.getSelfDomainCount());
        assertEquals(1, root.getChildrenCount());
    }

    @Test
    public void testScore() {
        root.addChild(PageNode.create("https://en.wikipedia.org/wiki/Wikipedia:About", 1));
        root.addChild(PageNode.create("https://foundation.wikimedia.org/wiki/Terms_of_Use", 1));
        assertEquals(1, root.getSelfDomainCount());
        assertEquals(2, root.getChildrenCount());

    }
}