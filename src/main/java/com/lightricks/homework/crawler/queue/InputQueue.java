package com.lightricks.homework.crawler.queue;

import com.lightricks.homework.crawler.model.PageMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class InputQueue {
    private final Queue<PageMessage> que = new ArrayDeque<>(1000);

    public boolean offer(PageMessage pageNode) {
        return que.offer(pageNode);
    }

    public PageMessage poll() throws InterruptedException {
        return que.poll();
    }

    public int size() {
        return que.size();
    }

    public void clear() {
        que.clear();
    }

}
