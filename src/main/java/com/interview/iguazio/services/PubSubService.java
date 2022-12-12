package com.interview.iguazio.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class PubSubService<T> {
    private static final Logger LOG = LoggerFactory.getLogger(PubSubService.class);
    private BlockingDeque<T> que = new LinkedBlockingDeque<>();

    public boolean offer(T s) {
        return que.offer(s);
    }

    public T take() {
        T res = null;
        try {
            res = que.take();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
        return res;
    }
}
