package com.lightricks.homework.crawler.utils;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class UrlUtilsTest {
// 'https://en.wikipedia.org/wiki/Horsepower'
    @Test
    public void testSimpleDomain() throws MalformedURLException {
        String actual = UrlUtils.getDomain("https://wikipedia.org/wiki/Horsepower");
        assertEquals("wikipedia.org", actual);
    }

    @Test
    public void testAliasedDomain() throws MalformedURLException {
        String actual = UrlUtils.getDomain("https://en.wikipedia.org/wiki/Horsepower");
        assertEquals("en.wikipedia.org", actual);
    }

    @Test
    public void testStrip3w() throws MalformedURLException {
        String actual = UrlUtils.getDomain("https://www.wikipedia.org/wiki/Horsepower");
        assertEquals("wikipedia.org", actual);
    }

    @Test
    public void testIsDomain() {
        assertTrue(UrlUtils.isUrl("https://www.wikipedia.org/wiki/Horsepower"));
        assertFalse(UrlUtils.isUrl("javascript:function()"));
    }

}