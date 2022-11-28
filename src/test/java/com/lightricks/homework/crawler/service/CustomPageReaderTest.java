package com.lightricks.homework.crawler.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class CustomPageReaderTest {
    @Test
    void testGetPage() {
        CustomPageReader reader = new CustomPageReader();
        reader.readPage("https://en.wikipedia.org/");
    }
}