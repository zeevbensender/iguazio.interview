package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageNode;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Profile("filePrinter")
@Primary
public class ScoreFilePrinter implements AggregatorPlugin {
    private static final Logger LOG = LoggerFactory.getLogger(ScoreFilePrinter.class);

    @Value("${outputFile}")
    private String outputFile;

    public void processPage(PageNode node) {
        System.out.println(node.getUrl() +
                "\t" + (node.getLevel() + 1) +
                "\t" + node.getSelfDomainCount() / node.getChildrenCount() +
                "\t" + node.getChildrenCount()
        );
    }
}
