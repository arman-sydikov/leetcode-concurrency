package com.leetcode.concurrency;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Phaser;

/**
 * 1242. Web Crawler Multithreaded
 * https://leetcode.com/problems/web-crawler-multithreaded
 * // This is the HtmlParser's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface HtmlParser {
 *     public List<String> getUrls(String url) {}
 * }
 *
 * @author ARMAN
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class Solution {

    private final Set<String> set = new HashSet<>();
    private String hostName;

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        // Set host
        hostName = getHostName(startUrl);

        // Set phaser
        Phaser phaser = new Phaser(1);

        // Add url
        addUrl(startUrl, htmlParser, phaser);

        // Arrive at this phaser and await others
        phaser.arriveAndAwaitAdvance();
        return new ArrayList<>(set);
    }

    private void addUrl(String parentUrl, HtmlParser htmlParser, Phaser phaser) {
        synchronized (this) {
            if (set.contains(parentUrl)) {
                return;
            }
            set.add(parentUrl);
        }
        // Add a new unarrived party to this phaser
        phaser.register();
        // Run htmlParser.getUrls() in a new thread
        new Thread(() -> {
            for (String childUrl : htmlParser.getUrls(parentUrl)) {
                if (hostName.equals(getHostName(childUrl))) {
                    addUrl(childUrl, htmlParser, phaser);
                }
            }
            // Arrive at this phaser
            phaser.arrive();
        }).start();
    }

    private String getHostName(String url) {
        return url.split("/")[2];
    }
}
