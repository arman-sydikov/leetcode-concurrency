package com.leetcode.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ARMAN
 */
class BoundedBlockingQueueTest {

    /**
     * Class we want to test
     */
    private BoundedBlockingQueue queue;

    /**
     * ThreadPool for producer threads
     */
    private ExecutorService producer;

    /**
     * ThreadPool for consumer threads
     */
    private ExecutorService consumer;

    /**
     * CountDownLatch
     */
    private CountDownLatch countDownLatch;

    @Test
    void firstTest() throws InterruptedException {

        // Initialize the queue with capacity = 2
        queue = new BoundedBlockingQueue(2);

        // Initialize the CountDownLatch with count = 8
        countDownLatch = new CountDownLatch(8);

        // Number of producer threads = 1
        producer = Executors.newSingleThreadExecutor();
        int[] elements = {1, 0, 2, 3, 4};
        for (int element : elements) {
            producer.execute(() -> {
                try {
                    queue.enqueue(element);
                    countDownLatch.countDown();
                } catch (InterruptedException ignored) {}
            });
        }

        // Number of consumer threads = 1
        consumer = Executors.newSingleThreadExecutor();
        for (int i=0; i<3; i++) {
            consumer.execute(() -> {
                try {
                    queue.dequeue();
                    countDownLatch.countDown();
                } catch (InterruptedException ignored) {}
            });
        }

        // Wait until the latch has counted down to zero
        countDownLatch.await();

        // Expecting 2 elements in the queue
        assertEquals(2, queue.size());
    }

    @Test
    void secondTest() throws InterruptedException {

        // Initialize the queue with capacity = 3
        queue = new BoundedBlockingQueue(3);

        // Initialize the CountDownLatch with count = 7
        countDownLatch = new CountDownLatch(7);

        // Number of producer threads = 3
        producer = Executors.newFixedThreadPool(3);
        int[] elements = {1, 0, 2, 3};
        for (int element : elements) {
            producer.execute(() -> {
                try {
                    queue.enqueue(element);
                    countDownLatch.countDown();
                } catch (InterruptedException ignored) {}
            });
        }

        // Number of consumer threads = 4
        consumer = Executors.newFixedThreadPool(4);
        for (int i=0; i<3; i++) {
            consumer.execute(() -> {
                try {
                    queue.dequeue();
                    countDownLatch.countDown();
                } catch (InterruptedException ignored) {}
            });
        }

        // Wait until the latch has counted down to zero
        countDownLatch.await();

        // Expecting 1 element in the queue
        assertEquals(1, queue.size());
    }
}