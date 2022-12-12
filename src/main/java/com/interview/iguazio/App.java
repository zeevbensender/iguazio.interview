package com.interview.iguazio;

import com.interview.iguazio.services.InputFileReader;
import com.interview.iguazio.services.PageProcessor;
import com.interview.iguazio.services.PageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@SpringBootApplication
@ComponentScan(basePackages = {"com.interview.iguazio"})
public class App implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    @Autowired
    private PageReader reader;

    @Autowired
    private PageProcessor pageProcessor;

    @Autowired
    private InputFileReader links;

    @Autowired
    private ExecutorService executor;

    StopWatch stopWatch = new StopWatch("Main");

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("input file path must be provided");
            System.exit(0);
        }
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);

    }

    @Override
    public void run(String... args) {

        try {
            stopWatch.start();
            List<CompletableFuture> cfs = links
                    .readInputFile(args[0])
                    .filter(lnk -> lnk != null && lnk.startsWith("http"))
                            .map(reader::readPageAsync).collect(Collectors.toList());
            cfs.stream().parallel().forEach(CompletableFuture::join);
            reader.readPage(null);
            while (pageProcessor.isRunning()) {
                Thread.sleep(10);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        LOG.info("\n\n {} \nRunning time: {} ms\n\n", stopWatch.prettyPrint(), stopWatch.getTotalTimeMillis());
//        System.out.println(stopWatch.shortSummary());
        executor.shutdownNow();
        System.exit(0);
    }


}
