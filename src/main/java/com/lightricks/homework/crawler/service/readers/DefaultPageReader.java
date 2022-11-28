package com.lightricks.homework.crawler.service.readers;

import com.lightricks.homework.crawler.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts links from HTML document
 */
@Service
public class DefaultPageReader extends AbstractPageReader {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultPageReader.class);
    private final Pattern elementPattern = Pattern.compile("(?i)<a([^>]+)>(.+?)</a>");
    private final Pattern linkPattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");

    private void extractHTMLLinks(String parentLink, String html) throws MalformedURLException {
        clear();
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
                if (child.startsWith("/")) {
                    child = baseAddress + child;
                } else if (child.startsWith("#")) {
                    child = parentLink + child;
                }
                offerLink(child);
            }
        }
    }

    /**
     * @param address of the page to read
     */
    @Override
    public void readPage(String address) {
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            if (!conn.getContentType().startsWith("text/html")) {
                return;
            }
            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            this.extractHTMLLinks(address, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Failed to get {} URL. Error: {}", address, e.getMessage());
        }
    }

}
