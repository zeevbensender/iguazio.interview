package com.lightricks.homework.crawler.queue;

import com.lightricks.homework.crawler.model.PageNode;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class InputQueue {
    private final Queue<PageNode> que = new LinkedBlockingQueue <>(1000);

    public boolean offer(PageNode pageNode) {
        return que.offer(pageNode);
    }

    public PageNode poll() {
        return que.poll();
    }

    public int size() {
        return que.size();
    }

    public PageNode peek() {
        return que.peek();
    }

}
