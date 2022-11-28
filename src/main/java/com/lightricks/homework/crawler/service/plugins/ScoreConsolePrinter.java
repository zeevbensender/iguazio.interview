package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.model.PageNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Profile("consolePrinter")
@Primary
public class ScoreConsolePrinter extends AbstractScorePrinter {
    @Override
    public void close() {
        System.out.println(output);
    }

}
