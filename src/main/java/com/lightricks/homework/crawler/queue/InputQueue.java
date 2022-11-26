package com.lightricks.homework.crawler.queue;

import com.lightricks.homework.crawler.model.PageNode;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class InputQueue {
    private final BlockingQueue<PageNode> que = new LinkedBlockingDeque<>(1000);

    public boolean offer(PageNode pageNode) {
        return que.offer(pageNode);
    }

    public PageNode take() throws InterruptedException {
        return que.take();
    }

    public int size() {
        return que.size();
    }

    public PageNode peek() {
        return que.peek();
    }

}
