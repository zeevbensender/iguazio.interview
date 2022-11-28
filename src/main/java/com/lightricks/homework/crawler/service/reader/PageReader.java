package com.lightricks.homework.crawler.service.reader;

public interface PageReader {
    void readPage(String url);

    String readLink ();

    boolean hasNext();

}
