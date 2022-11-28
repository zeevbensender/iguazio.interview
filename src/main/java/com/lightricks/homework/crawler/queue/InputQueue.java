package com.lightricks.homework.crawler.queue;

import com.lightricks.homework.crawler.model.PageMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Transfers messages from  ({@link com.lightricks.homework.crawler.service.plugins.CrawlingPlugin})
 * to page processor ({@link com.lightricks.homework.crawler.service.PageProcessor})
 * Max capacity is 1000 messages.
 */
@Service
public class InputQueue {
    private final Queue<PageMessage> que = new ArrayDeque<>(1000);

    public boolean offer(PageMessage pageNode) {
        return que.offer(pageNode);
    }

    public PageMessage poll() {
        return que.poll();
    }

    public int size() {
        return que.size();
    }

    public void clear() {
        que.clear();
    }
}
