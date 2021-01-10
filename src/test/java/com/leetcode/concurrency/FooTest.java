package com.leetcode.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ARMAN
 */
class FooTest {

    /**
     * Class we want to test
     */
    private Foo foo;

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
        foo = new Foo();
        sb = new StringBuffer();

        // Initialize the CountDownLatch with count = 3
        latch = new CountDownLatch(3);
    }

    @Test
    void firstTest() throws InterruptedException {

        // Start thread A
        new Thread(() -> {
            try {
                foo.first(() -> sb.append("first"));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Start thread B
        new Thread(() -> {
            try {
                foo.second(() -> sb.append("second"));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Start thread C
        new Thread(() -> {
            try {
                foo.third(() -> sb.append("third"));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Wait until the latch has counted down to zero
        latch.await();

        // Expecting output to be equal to "firstsecondthird"
        assertEquals("firstsecondthird", sb.toString());
    }

    @Test
    void secondTest() throws InterruptedException {

        // Start thread A
        new Thread(() -> {
            try {
                foo.first(() -> sb.append("first"));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Start thread B
        new Thread(() -> {
            try {
                foo.third(() -> sb.append("third"));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Start thread C
        new Thread(() -> {
            try {
                foo.second(() -> sb.append("second"));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Wait until the latch has counted down to zero
        latch.await();

        // Expecting output to be equal to "firstsecondthird"
        assertEquals("firstsecondthird", sb.toString());
    }
}