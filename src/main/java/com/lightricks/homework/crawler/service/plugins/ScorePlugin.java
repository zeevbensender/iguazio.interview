package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ScorePlugin implements AggregatorPlugin {
    private static final Logger LOG = LoggerFactory.getLogger(ScorePlugin.class);

    public void processPage(PageNode node) {
        System.out.println(node.getUrl() +
                "\t" + node.getLevel() +
                "\t" + node.getSelfDomainCount() / node.getChildrenCount() +
                "\t" + node.getChildrenCount()
        );
    }
}
