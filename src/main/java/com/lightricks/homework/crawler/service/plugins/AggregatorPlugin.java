package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageNode;

import java.util.Map;

public interface AggregatorPlugin {

    void aggregate(Map<String, PageNode> cache);
}
