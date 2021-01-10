package com.leetcode.concurrency;

import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

/**
 * 1117. Building H2O
 * https://leetcode.com/problems/building-h2o
 *
 * @author ARMAN
 */
public class H2O {

    /**
     * Semaphore for thread that needs to output "O"
     */
    private final Semaphore oxygen = new Semaphore(1);

    /**
     * Semaphore for thread that needs to output "H"
     */
    private final Semaphore hydrogen = new Semaphore(2);

    /**
     * Phaser
     */
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
