package com.lightricks.homework.crawler.service.readers;

public interface PageReader {
    void readPage(String url);

    String readLink ();

    boolean hasNext();

}
