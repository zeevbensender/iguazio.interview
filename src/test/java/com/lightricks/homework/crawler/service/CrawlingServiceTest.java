package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.TestConf;
import com.lightricks.homework.crawler.model.PageNode;
import com.lightricks.homework.crawler.queue.InputQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("simple")
@SpringJUnitConfig(classes = TestConf.class)
class CrawlingServiceTest {

    @Autowired
    private CachingService cache;

    @Autowired
    private CrawlingService crawlingService;

    @Autowired
    private InputQueue input;


    @Autowired
    private TestConf.MockedLinks mockedLinks;

    @BeforeEach
    void setUp() {
        input.offer(new PageNode("abc", 0)); //root
        mockedLinks.addChildren(new ArrayDeque<>(List.of("abcz", "dfs"))); //1st level children
        mockedLinks.addChildren(new ArrayDeque<>(List.of("second", "level"))); //2nd level children
        mockedLinks.addChildren(new ArrayDeque<>(List.of("second", "level", "second.child"))); //2nd level children
    }

    @Test
    public void testProcessPage() {
        crawlingService.processPage(1);
        assertEquals(3, cache.size());
    }
}