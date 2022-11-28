package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.service.reader.DefaultPageReader;
import org.junit.jupiter.api.Test;

class CustomPageReaderTest {
    @Test
    void testGetPage() {
        DefaultPageReader reader = new DefaultPageReader();
        reader.readPage("https://en.wikipedia.org/");
    }
}