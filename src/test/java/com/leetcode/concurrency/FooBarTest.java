package com.leetcode.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ARMAN
 */
class FooBarTest {

    /**
     * Class we want to test
     */
    private FooBar fooBar;

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

        // Initialize the CountDownLatch with count = 2
        latch = new CountDownLatch(2);
    }

    @Test
    void firstTest() throws InterruptedException {

        // Initialize with number of times to output "foobar"
        fooBar = new FooBar(1);

        // Start threads
        startThreads();

        // Expecting output to be equal to "foobar"
        assertEquals("foobar", sb.toString());
    }

    @Test
    void secondTest() throws InterruptedException {

        // Initialize with number of times to output "foobar"
        fooBar = new FooBar(2);

        // Start threads
        startThreads();

        // Expecting output to be equal to "foobarfoobar"
        assertEquals("foobarfoobar", sb.toString());
    }

    private void startThreads() throws InterruptedException {

        // Start thread A
        new Thread(() -> {
            try {
                fooBar.foo(() -> sb.append("foo"));
                latch.countDown();
            } catch (InterruptedException ignored) {
            }
        }).start();

        // Start thread B
        new Thread(() -> {
            try {
                fooBar.bar(() -> sb.append("bar"));
                latch.countDown();
            } catch (InterruptedException ignored) {
            }
        }).start();

        // Wait until the latch has counted down to zero
        latch.await();
    }
}