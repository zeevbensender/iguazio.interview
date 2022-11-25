package com.lightricks.homework.crawler;

import com.lightricks.homework.crawler.service.Crawler;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class crawler {


	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Usage: ");
			System.exit(0);
		}
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("com.lightricks.homework.crawler.service");
		Crawler service = context.getBean(Crawler.class);
		service.parseDocument(args[0]);
	}

}
