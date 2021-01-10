package com.leetcode.concurrency;

import java.util.concurrent.Semaphore;

/**
 * 1114. Print in Order
 * https://leetcode.com/problems/print-in-order
 *
 * @author ARMAN
 */
public class Foo {

    private final Semaphore second = new Semaphore(0);
    private final Semaphore third = new Semaphore(0);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        printFirst.run();
        second.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        second.acquire();
        printSecond.run();
        third.release();
    }

    public void third(Runnable printThird) throws InterruptedException {

        third.acquire();
        printThird.run();
    }
}
