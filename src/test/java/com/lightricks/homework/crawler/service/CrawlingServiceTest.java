package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.TestConf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("simple")
@SpringJUnitConfig(classes = TestConf.class)
class CrawlingServiceTest {
    @Autowired
    private CrawlingService crawlingService;
    @Autowired
    private CachingService cachingService;


    @BeforeEach
    void setUp() {
    }
    @Test
    public void testProcessPage() {
        Queue<String> nextLevelLinks = new ArrayDeque<>(List.of("zyama"));
        nextLevelLinks = crawlingService.processPage(nextLevelLinks, cachingService);
        assertEquals(2, nextLevelLinks.size());
    }
}