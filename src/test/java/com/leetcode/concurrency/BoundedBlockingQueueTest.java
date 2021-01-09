package com.leetcode.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoundedBlockingQueueTest {

    /**
     * Class we want to test
     */
    private BoundedBlockingQueue queue;
    private CountDownLatch countDownLatch;

    @Test
    void firstTest() throws InterruptedException {

        // Initialize the queue with capacity = 2
        queue = new BoundedBlockingQueue(2);

        // Initialize the CountDownLatch with count = 2
        countDownLatch = new CountDownLatch(2);

        // Number of producer threads = 1
        new Thread(() -> {
            try {
                queue.enqueue(1);
                queue.enqueue(0);
                queue.enqueue(2);
                queue.enqueue(3);
                queue.enqueue(4);
                // Count down
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Number of consumer threads = 1
        new Thread(() -> {
            try {
                queue.dequeue();
                queue.dequeue();
                queue.dequeue();
                // Count down
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();

        // 2 elements remaining in the queue
        int expected = 2;
        int actual = queue.size();

        assertEquals(expected, actual);
    }

    @Test
    void secondTest() throws InterruptedException {

        // Initialize the queue with capacity = 3
        queue = new BoundedBlockingQueue(3);

        // Initialize the CountDownLatch with count = 7
        countDownLatch = new CountDownLatch(7);

        // Number of producer threads = 3
        ExecutorService producer = Executors.newFixedThreadPool(3);
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
        ExecutorService consumer = Executors.newFixedThreadPool(4);
        for (int i=0; i<3; i++) {
            consumer.execute(() -> {
                try {
                    queue.dequeue();
                    countDownLatch.countDown();
                } catch (InterruptedException ignored) {}
            });
        }

        countDownLatch.await();

        // 1 element remaining in the queue
        int expected = 1;
        int actual = queue.size();

        assertEquals(expected, actual);
    }
}