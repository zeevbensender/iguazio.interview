package com.lightricks.homework.crawler;

import com.lightricks.homework.crawler.model.PageNode;
import com.lightricks.homework.crawler.service.CachingService;
import com.lightricks.homework.crawler.service.CrawlingService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

		PageNode page = new PageNode(root, 0);

		service.processPage(page, levels, cache);

	}

}
