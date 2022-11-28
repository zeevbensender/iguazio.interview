package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageNode;

public interface AggregatorPlugin {
    void aggregatePage(PageNode node);

    void close();
}
