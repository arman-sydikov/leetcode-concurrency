package com.leetcode.concurrency;

/**
 * @author 阿尔曼
 */
public class Foo {

    private volatile boolean firstFinished = false;
    private volatile boolean secondFinished = false;

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        firstFinished = true;
    }

    public void second(Runnable printSecond) throws InterruptedException {

        while (!firstFinished) {
            Thread.sleep(100);
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        secondFinished = true;
    }

    public void third(Runnable printThird) throws InterruptedException {

        while (!secondFinished) {
            Thread.sleep(100);
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
