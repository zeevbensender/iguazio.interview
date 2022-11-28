package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageNode;

public abstract class AbstractScorePrinter implements AggregatorPlugin {

    protected StringBuilder output = new StringBuilder("url\tdepth\tratio\tlinks count");

    @Override
    public void aggregatePage(PageNode node) {
        output.append('\n');
        output.append(node.getUrl()).append('\t');
        output.append(node.getLevel()).append('\t');
        output.append(node.getSelfDomainCount() / node.getChildrenCount()).append('\t');
        output.append((int) node.getChildrenCount());
    }
}
