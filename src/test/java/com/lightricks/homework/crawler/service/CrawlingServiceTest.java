package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.TestConf;
import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.queue.InputQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ActiveProfiles({"simple", "consolePrinter" })
@SpringJUnitConfig(classes = TestConf.class)
class CrawlingServiceTest {

    @Autowired
    private CachingService cache;
    @Autowired
    private PageProcessor crawlingService;
    @Autowired
    private InputQueue input;
    @Autowired
    private TestConf.MockedLinks mockedLinks;

    @BeforeEach
    void setUp() {
        input.clear();
        mockedLinks.links.clear();
        cache.clear();
        input.offer(new PageMessage("http://abc.com", 0)); //root
        mockedLinks.addChildren(new ArrayDeque<>(List.of("http://abcz.com", "http://dfs.com"))); //1st level children; parent: abc
        mockedLinks.addChildren(new ArrayDeque<>(List.of("http://second.com", "http://level.com"))); //2nd level children; parent: abcz
        mockedLinks.addChildren(new ArrayDeque<>(List.of("http://another.second.com", "http://level.com" /*duplicate*/, "http://second.child.com"))); //2nd level children; parent: level
        mockedLinks.addChildren(new ArrayDeque<>(List.of("http://secondbee.com", "http://levelqqq.com", "http://oops.child.com"))); //2nd level children
        mockedLinks.addChildren(new ArrayDeque<>(List.of("secondbee", "levelqqq", "oops.child"))); //2nd level children
        mockedLinks.addChildren(new ArrayDeque<>(List.of("secondbee", "levelqqq", "oops.child"))); //2nd level children
        mockedLinks.addChildren(new ArrayDeque<>(List.of("secondbee", "levelqqq", "oops.child"))); //2nd level children
    }

    @Test
    public void testProcessPage() {
        crawlingService.processPage(1);
        assertEquals(3, cache.size());
    }
    @Test
    public void testProcessPageNoDuplicates() {
        crawlingService.processPage(2);
        assertEquals(7, cache.size());
    }

}