package com.lightricks.homework.crawler;

import com.lightricks.homework.crawler.service.CachingService;
import com.lightricks.homework.crawler.service.PageReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.ArrayDeque;
import java.util.Queue;

@Configuration
@ComponentScan({"com.lightricks.homework.crawler"})
public class TestConf {


    private MockedLinks mockedLinks = new MockedLinks();

    @Bean
    @Profile("simple")
    @Primary
    public PageReader pageReaderService() {
        return new PageReader() {
            Queue<String> children;
/*
            Queue<Queue<String>> pages = new ArrayDeque<>(
                    List.of(
                            new ArrayDeque<>(List.of("abc", "dfs")),
                            new ArrayDeque<>(List.of("abg", "doloy", "putina", "svoboda")),
                            new ArrayDeque<>(List.of("ukraine", "am", "hai"))
                    )
            );
*/

            @Override
            public void readPage(String url) {
                children = mockedLinks.getChildren();
                System.out.println("!!!!!!!!!!!!! CALLED for " + url);
            }

            @Override
            public String readLink() {
                return children.remove();
            }

            @Override
            public boolean hasNext() {
                return !children.isEmpty();
            }
        };
    }

    @Bean
    @Profile("simple")
    @Primary
    public CachingService getCachingService() {
        return new CachingService();
    }

    @Bean
    public MockedLinks getMockedLinks() {
        return mockedLinks;
    }


    public class MockedLinks {
        public Queue<Queue<String>> links = new ArrayDeque<>();
        public void addChildren(Queue<String> links) {

            this.links.offer(links);
        }

        public Queue<String> getChildren() {
            return this.links.poll();
        }
    }
}
