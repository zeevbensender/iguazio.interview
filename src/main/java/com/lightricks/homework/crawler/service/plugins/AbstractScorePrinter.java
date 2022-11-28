package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageNode;
import com.lightricks.homework.crawler.service.AppContext;

import java.util.Map;

/**
 * Calculates score ond prints links to configured output either {@link ScoreFilePrinter} or {@link ScoreConsolePrinter}
 */
public abstract class AbstractScorePrinter implements AggregatorPlugin {
    protected StringBuilder output = new StringBuilder("url\tdepth\tratio\tlinks count");
    protected final AppContext context;

    protected AbstractScorePrinter(AppContext context) {
        this.context = context;
    }

    private void aggregatePage(PageNode node) {
        float score = node.getChildrenCount() == 0
                ? 0
                : node.getSelfDomainCount() / node.getChildrenCount();
        output.append('\n');
        output.append(node.getUrl()).append('\t');
        output.append(node.getLevel()).append('\t');
        output.append(score).append('\t');
        output.append((int) node.getChildrenCount());
    }

    protected abstract void print();

    @Override
    public void aggregate(Map<String, PageNode> cache) {
        cache.values().stream().filter(
                p -> p.getLevel() <= context.getMaxLevel())
                .forEach(this::aggregatePage);
        print();
    }
}
