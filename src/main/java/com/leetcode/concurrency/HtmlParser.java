package com.leetcode.concurrency;

import java.util.List;

/**
 * // This is the HtmlParser's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface HtmlParser {
 *     public List<String> getUrls(String url) {}
 * }
 *
 * @author ARMAN
 */
public interface HtmlParser {

    List<String> getUrls(String url);

}
