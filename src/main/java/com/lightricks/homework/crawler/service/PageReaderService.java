package com.lightricks.homework.crawler.service;

public interface PageReaderService {
    void readPage(String url);

    String readLink ();

    boolean hasNext();
}
