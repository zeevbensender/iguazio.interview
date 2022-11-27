package com.lightricks.homework.crawler;

import com.lightricks.homework.crawler.model.PageMessage;
import com.lightricks.homework.crawler.queue.InputQueue;
import com.lightricks.homework.crawler.service.PageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.util.Scanner;

/*


@SpringBootApplication
@ComponentScan(basePackages = {"com.lupo.drivebox.app",
		"com.lupo.drivebox.drive",
		"com.lupo.drivebox.web",
		"com.lupo.drivebox.service",
		"com.lupo.drivebox.common"
})
public class DriveboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriveboxApplication.class, args);
	}

}



 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.lightricks.homework.crawler"})
public class crawler implements CommandLineRunner {

	@Autowired
	private InputQueue inputQue;
	@Autowired
	private PageProcessor service;
	public static void main(String[] args) {

		if(args.length < 2) {
			System.err.println("Usage: java -jar <path to jar file>/crawler-0.0.1-SNAPSHOT.jar <URL> <level> [output file]");
			System.exit(0);
		}
		int levels = -1;
		try {
			levels = Integer.parseInt(args[1]);
		}catch (ClassCastException e) {
			System.err.println("Second argument must be integer");
			System.exit(0);
		}
		if(levels > 3) {
			Scanner userInput = new Scanner(System.in);  // Create a Scanner object
			System.out.println("Running the application with level greater than 3 may crash your system.");
			System.out.println("Would you like to run it on your own risk? (Yy/Nn)");
			String userResponse = userInput.nextLine().toLowerCase();
			if(!userResponse.equals("y")) {
				System.exit(0);
			}
			System.out.println("Good luck buddy and be patient! It's gonna take time");
		}
		SpringApplication app = new SpringApplication(crawler.class);
		ConfigurableEnvironment environment = new StandardEnvironment();
		if(args.length == 3) {
			environment.setActiveProfiles("filePrinter");
		} else {
			environment.setActiveProfiles("consolePrinter");
		}
		app.setEnvironment(environment);
		app.run(args);
//		SpringApplication.run(crawler.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		String root = args[0];
		String output = null;
		if(args.length == 3) {
			output = args[2];
		}

		inputQue.offer(new PageMessage(root, 0));
		service.processPage(Integer.parseInt(args[1]));
	}
}
