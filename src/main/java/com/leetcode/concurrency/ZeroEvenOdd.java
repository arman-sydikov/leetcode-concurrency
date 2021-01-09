package com.leetcode.concurrency;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author 阿尔曼
 */
public class ZeroEvenOdd {
    private int n;
    private final Semaphore zero = new Semaphore(1);
    private final Semaphore odd = new Semaphore(0);
    private final Semaphore even = new Semaphore(0);


    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i=1; i<=n; i++) {
            zero.acquire();
            printNumber.accept(0);
            if (i%2==0) {
                even.release();
            } else {
                odd.release();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i=1; i<=n; i+=2) {
            odd.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i=2; i<=n; i+=2) {
            even.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }
}
