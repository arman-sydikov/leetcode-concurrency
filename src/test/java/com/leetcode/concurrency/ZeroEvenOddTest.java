package com.leetcode.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ARMAN
 */
class ZeroEvenOddTest {

    /**
     * Class we want to test
     */
    private ZeroEvenOdd zeroEvenOdd;

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

        // Initialize StringBuffer
        sb = new StringBuffer();

        // Initialize CountDownLatch
        latch = new CountDownLatch(3);
    }

    @Test
    void firstTest() throws InterruptedException {

        // Initialize ZeroEvenOdd
        zeroEvenOdd = new ZeroEvenOdd(2);

        // Start threads
        startThreads();

        // Compare expected with actual value
        assertEquals("0102", sb.toString());
    }

    @Test
    void secondTest() throws InterruptedException {

        // Initialize ZeroEvenOdd
        zeroEvenOdd = new ZeroEvenOdd(5);

        // Start threads
        startThreads();

        // Compare expected with actual value
        assertEquals("0102030405", sb.toString());
    }

    private void startThreads() throws InterruptedException {
        // Start thread A
        new Thread(() -> {
            try {
                zeroEvenOdd.zero((x) -> sb.append(x));
                latch.countDown();
            } catch (InterruptedException ignored) {
            }
        }).start();

        // Start thread B
        new Thread(() -> {
            try {
                zeroEvenOdd.even((x) -> sb.append(x));
                latch.countDown();
            } catch (InterruptedException ignored) {
            }
        }).start();

        // Start thread C
        new Thread(() -> {
            try {
                zeroEvenOdd.odd((x) -> sb.append(x));
                latch.countDown();
            } catch (InterruptedException ignored) {
            }
        }).start();

        // Wait until the latch has counted down to zero
        latch.await();
    }
}