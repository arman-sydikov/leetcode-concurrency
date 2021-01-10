package com.leetcode.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ARMAN
 */
class DiningPhilosophersTest {

    /**
     * Class we want to test
     */
    private DiningPhilosophers philosopher;

    /**
     * CountDownLatch
     */
    private CountDownLatch countDownLatch;

    @BeforeEach
    void setUp() {
        philosopher = new DiningPhilosophers();
    }

    @Test
    void firstTest() throws InterruptedException {

        // Number of philosophers
        int p = 5;

        // Number of times each philosopher will call the function
        int n = 1;

        // Initialize the CountDownLatch with count = p * n
        countDownLatch = new CountDownLatch(p*n);

        for (int i = 0; i<p; i++) {
            int id = i;
            new Thread(() -> {
                for (int j=0; j<n; j++) {
                    try {
                        philosopher.wantsToEat(
                                id,
                                () -> System.out.println("Philosopher " + id + " picks left fork"),
                                () -> System.out.println("Philosopher " + id + " picks right fork"),
                                () -> System.out.println("Philosopher " + id + " eats"),
                                () -> System.out.println("Philosopher " + id + " puts left fork"),
                                () -> System.out.println("Philosopher " + id + " puts right fork"));
                        countDownLatch.countDown();
                    } catch (InterruptedException ignored) {}
                }
            }).start();
        }

        // Wait until the latch has counted down to zero
        countDownLatch.await();

        // Expecting 0 count in the countDownLatch
        assertEquals(0, countDownLatch.getCount());
    }
}