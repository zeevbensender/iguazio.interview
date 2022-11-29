package com.lightricks.homework.crawler.service.reader;

import com.lightricks.homework.crawler.utils.UrlUtils;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class AbstractPageReader implements PageReader{
    private Queue<String> children = new ArrayDeque<>();

    public String readLink() {
        return children.poll();
    }

    @Override
    public boolean hasNext() {
        return !children.isEmpty();
    }

    protected void clear() {
        children.clear();
    }

    protected void offerLink(String link) {
        //we don't pull links directly from the source because not all of them are valid
        //pulling links directly may cause to false positive hasNext
        if (!UrlUtils.isUrl(link) ||
                link.endsWith(".pdf") ||
                link.endsWith(".PDF") ||
                link.endsWith(".jpg") ||
                link.endsWith(".JPG") ||
                link.endsWith(".gif") ||
                link.endsWith(".GIF") ||
                link.endsWith(".xml") ||
                link.endsWith(".XML") //This does not cover all non-html content types.
            //content types other than html will be filtered out later.
            // this condition is to avoid annoying error logs
        ) {
            return;
        }
        children.offer(link);
    }

}
