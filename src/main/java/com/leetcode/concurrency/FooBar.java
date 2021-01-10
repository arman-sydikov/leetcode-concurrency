package com.leetcode.concurrency;

import java.util.concurrent.Semaphore;

/**
 * 1115. Print FooBar Alternately
 * https://leetcode.com/problems/print-foobar-alternately
 *
 * @author ARMAN
 */
public class FooBar {

    /**
     * Number of times to output "foobar"
     */
    private final int n;

    /**
     * Semaphore for thread that needs to output "foo"
     */
    private final Semaphore foo = new Semaphore(1);

    /**
     * Semaphore for thread that needs to output "bar"
     */
    private final Semaphore bar = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            foo.acquire();
            printFoo.run();
            bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            bar.acquire();
            printBar.run();
            foo.release();
        }
    }
}
