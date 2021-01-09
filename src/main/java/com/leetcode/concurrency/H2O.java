package com.leetcode.concurrency;

import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

/**
 * @author 阿尔曼
 */
public class H2O {

    private final Semaphore oxygen = new Semaphore(1);
    private final Semaphore hydrogen = new Semaphore(2);
    private final Phaser phaser = new Phaser(3);

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

        hydrogen.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        phaser.arriveAndAwaitAdvance();
        hydrogen.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {

        oxygen.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        phaser.arriveAndAwaitAdvance();
        oxygen.release();
    }
}
