package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.TestConf;
import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.model.PageNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = TestConf.class)
class CachingServiceTest {

    @Autowired
    private CachingService cache;

    @BeforeEach
    void setUp() {
        cache.clear();
    }

    @Test
    public void addRootTest() {
        String url = "https://en.wikipedia.org/";
        cache.addLink(new PageMessage(url, null, 0, false));
        assertEquals(1, cache.size());
        assertTrue(cache.contains(url));
        PageNode root = cache.get(url);
        assertNotNull(root);
        assertEquals(0, root.getChildrenCount());
        assertEquals(url, root.getUrl());
    }

    @Test
    public void addChildTest() {
        String rootUrl = "https://en.wikipedia.org/";
        cache.addLink(new PageMessage(rootUrl, null, 0, false));
        String childUrl = "https://en.wikipedia.org/wiki/Wikipedia:About";
        cache.addLink(new PageMessage(childUrl, rootUrl, 1, true));
        assertEquals(2, cache.size());
        assertTrue(cache.contains(rootUrl));
        assertTrue(cache.contains(childUrl));
        PageNode root = cache.get(rootUrl);
        assertNotNull(root);
        assertEquals(1, root.getChildrenCount());
        assertEquals(1, root.getSelfDomainCount());
        PageNode child = cache.get(childUrl);
        assertEquals(childUrl, child.getUrl());
    }
}