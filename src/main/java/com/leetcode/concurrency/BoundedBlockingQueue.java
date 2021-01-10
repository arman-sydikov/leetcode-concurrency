package com.leetcode.concurrency;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1188. Design Bounded Blocking Queue
 * https://leetcode.com/problems/design-bounded-blocking-queue
 *
 * @author ARMAN
 */
public class BoundedBlockingQueue {

    /**
     * FIFO queue
     */
    private final Queue<Integer> queue;

    /**
     * Maximum capacity of the queue
     */
    private final int capacity;

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public void enqueue(int element) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == capacity) {
                wait();
            }
            queue.add(element);
            notify();
        }
    }

    public int dequeue() throws InterruptedException {
        synchronized (this) {
            while (queue.size() == 0) {
                wait();
            }
            int first = queue.remove();
            notify();
            return first;
        }
    }

    public int size() {
        synchronized (this) {
            return queue.size();
        }
    }
}
