package com.interview.iguazio.service;

import com.interview.iguazio.TestConf;
import com.interview.iguazio.model.PageMessage;
import com.interview.iguazio.model.PageNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = TestConf.class)
@ActiveProfiles({"consolePrinter" })
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
        cache.cacheLink(new PageMessage(url, null, 0));
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
        cache.cacheLink(new PageMessage(rootUrl, null, 0));
        String childUrl = "https://en.wikipedia.org/wiki/Wikipedia:About";
        cache.cacheLink(new PageMessage(childUrl, rootUrl, 1));
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