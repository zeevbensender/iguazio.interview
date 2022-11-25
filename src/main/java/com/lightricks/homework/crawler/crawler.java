package com.lightricks.homework.crawler;

import com.lightricks.homework.crawler.service.CachingService;
import com.lightricks.homework.crawler.service.CrawlingService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayDeque;
import java.util.Queue;

public class crawler {


	public static void main(String[] args) {
		if(args.length < 2) {
			System.err.println("Usage: ");
			System.exit(0);
		}
		String root = args[0];
		int levels = -1;
		try {
			levels = Integer.parseInt(args[1]);
		}catch (ClassCastException e) {
			System.err.println("Second argument must be integer");
			System.exit(0);
		}
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("com.lightricks.homework.crawler.service");
		CrawlingService service = context.getBean(CrawlingService.class);
		CachingService cache = context.getBean(CachingService.class);
		Queue<String> nextLevelLinks = new ArrayDeque<>();
		nextLevelLinks.offer(root);

		for(int i = 0; i < levels; i++) {
			nextLevelLinks = service.processPage(nextLevelLinks, cache);
		}
	}

}
