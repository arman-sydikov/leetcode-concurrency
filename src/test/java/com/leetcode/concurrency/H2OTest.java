package com.leetcode.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ARMAN
 */
class H2OTest {

    /**
     * Class we want to test
     */
    private H2O h2o;

    /**
     * StringBuffer
     */
    private StringBuffer sb;

    /**
     * CountDownLatch
     */
    private CountDownLatch latch;

    @BeforeEach
    void setUp() {

        // Initialize H2O
        h2o = new H2O();

        // Initialize StringBuffer
        sb = new StringBuffer();
    }

    @Test
    void firstTest() throws InterruptedException {

        // Start threads
        startThreads("HOH");

        // Prepare expected list
        List<String> expected = new ArrayList<>();
        expected.add("OHH");
        expected.add("HOH");
        expected.add("HHO");

        // Check if expected list contains actual value
        assertTrue(expected.contains(sb.toString()));
    }

    @Test
    void secondTest() throws InterruptedException {

        // Start threads
        startThreads("OOHHHH");

        // Prepare expected list
        List<String> expected = new ArrayList<>();
        expected.add("OHHOHH");
        expected.add("OHHHOH");
        expected.add("OHHHHO");
        expected.add("HOHOHH");
        expected.add("HOHHOH");
        expected.add("HOHHHO");
        expected.add("HHOOHH");
        expected.add("HHOHOH");
        expected.add("HHOHHO");

        // Check if expected list contains actual value
        assertTrue(expected.contains(sb.toString()));
    }

    private void startThreads(String threads) throws InterruptedException {

        // Initialize CountDownLatch
        latch = new CountDownLatch(threads.length());

        for (char thread : threads.toCharArray()) {
            if ('H' == thread) {
                // Start hydrogen thread
                new Thread(() -> {
                    try {
                        h2o.hydrogen(() -> sb.append("H"));
                        latch.countDown();
                    } catch (InterruptedException ignored) {
                    }
                }).start();
            } else {
                // Start oxygen thread
                new Thread(() -> {
                    try {
                        h2o.oxygen(() -> sb.append("O"));
                        latch.countDown();
                    } catch (InterruptedException ignored) {
                    }
                }).start();
            }
        }

        // Wait until the latch has counted down to zero
        latch.await();
    }
}