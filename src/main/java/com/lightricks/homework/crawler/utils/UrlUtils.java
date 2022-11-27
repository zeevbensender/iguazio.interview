package com.lightricks.homework.crawler.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {
    public static String getDomain(String address) throws MalformedURLException {
        String domain = new URL(address).getHost();
        if(domain.startsWith("www.")) {
            domain = domain.substring(4);
        }
        return domain;
    }

    public static boolean isDomain(String address) {
        try {
            getDomain(address);
        } catch (MalformedURLException ignore) {
            return false;
        }
        return true;
    }

    public static boolean isSameDomain(String address1, String address2) throws MalformedURLException {
        return getDomain(address1).equals(getDomain(address2));
    }
}
