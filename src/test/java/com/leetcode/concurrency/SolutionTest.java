package com.leetcode.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * @author ARMAN
 */
class SolutionTest {

    /**
     * Class we want to test
     */
    private Solution solution;

    /**
     * HtmlParser interface
     */
    private HtmlParser htmlParser;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    void test1() throws IOException {
        Input input = new Input("src/test/resources/com/leetcode/concurrency/solution/in1");
        htmlParser = new HtmlParserImpl(input.urls, input.edges);

        List<String> actual = solution.crawl(input.startUrl, htmlParser);
        Collections.sort(actual);

        assertIterableEquals(input.expected, actual);
    }

    @Test
    void test2() throws IOException {
        Input input = new Input("src/test/resources/com/leetcode/concurrency/solution/in2");
        htmlParser = new HtmlParserImpl(input.urls, input.edges);

        List<String> actual = solution.crawl(input.startUrl, htmlParser);
        Collections.sort(actual);

        assertIterableEquals(input.expected, actual);
    }

    @Test
    void test3() throws IOException {
        Input input = new Input("src/test/resources/com/leetcode/concurrency/solution/in3");
        htmlParser = new HtmlParserImpl(input.urls, input.edges);

        List<String> actual = solution.crawl(input.startUrl, htmlParser);
        Collections.sort(actual);

        assertIterableEquals(input.expected, actual);
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void test4() throws IOException {
        Input input = new Input("src/test/resources/com/leetcode/concurrency/solution/in4");
        htmlParser = new HtmlParserImpl(input.urls, input.edges);

        List<String> actual = solution.crawl(input.startUrl, htmlParser);
        Collections.sort(actual);

        assertIterableEquals(input.expected, actual);
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void test5() throws IOException {
        Input input = new Input("src/test/resources/com/leetcode/concurrency/solution/in5");
        htmlParser = new HtmlParserImpl(input.urls, input.edges);

        List<String> actual = solution.crawl(input.startUrl, htmlParser);
        Collections.sort(actual);

        assertIterableEquals(input.expected, actual);
    }

    private static class HtmlParserImpl implements HtmlParser {

        private final Map<String, List<String>> map;

        private HtmlParserImpl(String[] urls, int[][] edges) {
            this.map = new HashMap<>();
            for (String url : urls) {
                map.put(url, new LinkedList<>());
            }
            for (int[] edge : edges) {
                map.get(urls[edge[0]]).add(urls[edge[1]]);
            }
        }

        @Override
        public List<String> getUrls(String url) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException ignored) {
            }
            return map.get(url);
        }
    }

    private static class Input {
        private final String[] urls;
        private final int[][] edges;
        private final String startUrl;
        private final List<String> expected = new LinkedList<>();

        private Input(String fileName) throws IOException {
            // Read from file
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            // Set urls
            urls = lines.get(0).replaceAll("[\\[\\]\"]", "").split(",");
            // Set edges
            String[] tuples =  lines.get(1).replace("[[", "").replace("]]", "").split("],\\[");
            edges = new int[tuples.length][];
            for (int i=0; i<tuples.length; i++) {
                String[] tuple = tuples[i].split(",");
                int[] edge = {Integer.parseInt(tuple[0]), Integer.parseInt(tuple[1])};
                edges[i] = edge;
            }
            // Set startUrl
            startUrl = lines.get(2).replaceAll("\"", "");
            // Set expected
            expected.addAll(Arrays.asList(lines.get(3).replaceAll("[\\[\\]\"]", "").split(",")));
        }
    }
}