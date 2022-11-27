package com.lightricks.homework.crawler.service;

public interface PageReader {
    void readPage(String url);

    String readLink ();

    boolean hasNext();
}
