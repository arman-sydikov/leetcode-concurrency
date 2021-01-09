package com.leetcode.concurrency;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 阿尔曼
 */
public class FooBar {
    private int n;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final AtomicBoolean odd = new AtomicBoolean(true);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            synchronized (this) {
                wait();
            }
            lock.lock();
            try {
                while (!odd.get()) {
                    condition.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                odd.set(false);
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            lock.lock();
            try {
                while (odd.get()) {
                    condition.await();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                odd.set(true);
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
