package com.lightricks.homework.crawler.service;

import com.lightricks.homework.crawler.utils.UrlUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.w3c.dom.html.HTMLLinkElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomPageReader implements PageReader {
    private Queue<String> children = new ArrayDeque<>();
    private static final Logger LOG = LoggerFactory.getLogger(CustomPageReader.class);

    private final Pattern elementPattern = Pattern.compile("(?i)<a([^>]+)>(.+?)</a>");
    private final Pattern linkPattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");

    public void extractHTMLLinks(String parentLink, String html) throws MalformedURLException {
        children.clear();

        Matcher elementMatcher = elementPattern.matcher(html);
        if (parentLink.endsWith("/")) {
            parentLink = parentLink.substring(0, parentLink.length() - 1);
        }
        String baseAddress = UrlUtils.getBaseAddress(parentLink);

        while (elementMatcher.find()) {

            String href = elementMatcher.group(1);     // get the values of href
            Matcher linkMatcher = linkPattern.matcher(href);

            while (linkMatcher.find()) {

                String child = linkMatcher.group(1);
                child = child.substring(1, child.length() - 1);
                if (!UrlUtils.isDomain(child) ||
                        child.endsWith(".pdf") ||
                        child.endsWith(".PDF") ||
                        child.endsWith(".jpg") ||
                        child.endsWith(".JPG") ||
                        child.endsWith(".gif") ||
                        child.endsWith(".GIF") ||
                        child.endsWith(".xml") ||
                        child.endsWith(".XML")  //content types other than html will be filtered out later.
                    // this condition is to avoid annoying error logs
                ) {
                    continue;
                }
                if (child.startsWith("/")) {
                    child = baseAddress + child;
                } else if (child.startsWith("#")) {
                    child = parentLink + child;
                }
                children.offer(child);
            }

        }
    }

    @Override
    public void readPage(String address) {
        try {
            URL url = new URL(address);
            URLConnection hc = url.openConnection();
            hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            if(!hc.getContentType().startsWith("text/html")) {
                return;
            }
            Scanner sc = new Scanner(hc.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            this.extractHTMLLinks(address, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Failed to get {} URL. Error: {}", address, e.getMessage());
        }
    }

    public String readLink() {
        return children.poll();
    }

    @Override
    public boolean hasNext() {
        return !children.isEmpty();
    }
}
