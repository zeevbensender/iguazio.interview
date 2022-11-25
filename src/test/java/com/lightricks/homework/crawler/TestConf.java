package com.lightricks.homework.crawler;

import com.lightricks.homework.crawler.service.PageReaderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

@Configuration
@ComponentScan({"com.lightricks.homework.crawler.service"})
public class TestConf {


    @Bean
    @Profile("simple")
    @Primary
    public PageReaderService pageReaderService() {
        return new PageReaderService() {
            Queue<String> children;
            Queue<Queue<String>> pages = new ArrayDeque<>(
                    List.of(
                            new ArrayDeque<>(List.of("abc", "dfs")),
                            new ArrayDeque<>(List.of("abg", "doloy", "putina", "svoboda")),
                            new ArrayDeque<>(List.of("ukraine", "am", "hai"))
                    )
            );

            @Override
            public void readPage(String url) {
                children = pages.poll();
            }

            @Override
            public String readLink() {
                return children.poll();
            }

            @Override
            public boolean hasNext() {
                return !children.isEmpty();
            }
        };
    }
}
