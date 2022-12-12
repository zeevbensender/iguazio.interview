package com.interview.iguazio.services;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@EnableAsync
public class PageProcessor {
    private static final Logger LOG = getLogger(PageProcessor.class);
    private final PubSubService<Message> pubsub;

    private boolean running = true;

    public PageProcessor(@Autowired PubSubService pubsub) {
        this.pubsub = pubsub;
    }

    public boolean isRunning() {
        return running;
    }

    @Async
    @Scheduled(initialDelay = 1000, fixedDelay = Long.MAX_VALUE)
    public void run() {
        int count = 1;
        LOG.info("<<< STARTING >>>");
        while(true) {
            Message msg = pubsub.take();
            if(msg.isPoisoned()) {
                break;
            }
            System.out.println((count++) + " " + msg);
        }
        LOG.info("<<< EXITING >>>");
        running = false;

    }
}
