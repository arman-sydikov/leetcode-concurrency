package com.leetcode.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ARMAN
 */
class FizzBuzzTest {

    /**
     * Class we want to test
     */
    private FizzBuzz fizzBuzz;

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
        latch = new CountDownLatch(4);
    }

    @Test
    void firstTest() throws InterruptedException {

        // Initialize FizzBuzz
        fizzBuzz = new FizzBuzz(15);

        // Start thread A
        new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> sb.append("fizz").append(" "));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Start thread B
        new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> sb.append("buzz").append(" "));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Start thread C
        new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> sb.append("fizzbuzz").append(" "));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Start thread D
        new Thread(() -> {
            try {
                fizzBuzz.number((x) -> sb.append(x).append(" "));
                latch.countDown();
            } catch (InterruptedException ignored) {}
        }).start();

        // Wait until the latch has counted down to zero
        latch.await();

        // Compare expected with actual value
        assertEquals("1 2 fizz 4 buzz fizz 7 8 fizz buzz 11 fizz 13 14 fizzbuzz ", sb.toString());
    }
}