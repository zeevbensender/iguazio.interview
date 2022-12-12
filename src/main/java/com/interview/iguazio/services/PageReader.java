package com.interview.iguazio.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Service
public class PageReader {
    private static final Logger LOG = LoggerFactory.getLogger(PageReader.class);
    private final PubSubService<Message> pubSub;

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public PageReader(@Autowired PubSubService pubSub) {
        this.pubSub = pubSub;
    }

    @Async("taskExecutor")
    public CompletableFuture readPageAsync(String address) {

        //Create request
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(address))
                .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                .build();
        //Execute the request
        CompletableFuture<HttpResponse<String>>
                asyncResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        //Get response CompletableFutures
        CompletableFuture<String> cfBody = asyncResponse.thenApply(HttpResponse::body);
        CompletableFuture<Integer> cfStatus = asyncResponse.thenApply(HttpResponse::statusCode);
        cfBody.thenCombine(cfStatus, (body, status) -> new Message(body, address, Thread.currentThread().getName(), status))
                .thenApply(pubSub::offer);
        return asyncResponse;
    }

    @Async("taskExecutor")
    public CompletableFuture readPage(String address) {
//        LOG.info("ADDRESS: {}", address);
        try {
            if (address == null) {
                pubSub.offer(Message.poisonedPill());
                return CompletableFuture.supplyAsync(() -> "do nothing");
            }
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            if (!conn.getContentType().startsWith("text/")) {
                return CompletableFuture.supplyAsync(() -> "do nothing");
            }
            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            pubSub.offer(new Message(sb.toString(), address, Thread.currentThread().getName(), 0));
        } catch (FileNotFoundException e) {
            LOG.error("Page not found: {}. Error: {}", address, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Failed to get {} URL. Error: {}", address, e.getMessage(), e);
        }
        return CompletableFuture.supplyAsync(() -> "do nothing");
    }
}
