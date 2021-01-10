package com.leetcode.concurrency;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * 1195. Fizz Buzz Multithreaded
 * https://leetcode.com/problems/fizz-buzz-multithreaded
 *
 * @author ARMAN
 */
public class FizzBuzz {
    private int n;
    private final Semaphore number = new Semaphore(1);
    private final Semaphore fizz = new Semaphore(0);
    private final Semaphore buzz = new Semaphore(0);
    private final Semaphore fizzbuzz = new Semaphore(0);

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i=3; i<=n; i+=3) {
            if (i%5==0) {
                continue;
            }
            fizz.acquire();
            printFizz.run();
            number.release();
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i=5; i<=n; i+=5) {
            if (i%3==0) {
                continue;
            }
            buzz.acquire();
            printBuzz.run();
            number.release();
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i=15; i<=n; i+=15) {
            fizzbuzz.acquire();
            printFizzBuzz.run();
            number.release();
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i=1; i<=n; i++) {
            number.acquire();
            if (i%15==0) {
                fizzbuzz.release();
            } else if (i%3==0) {
                fizz.release();
            } else if (i%5==0) {
                buzz.release();
            } else {
                printNumber.accept(i);
                number.release();
            }
        }
    }
}
