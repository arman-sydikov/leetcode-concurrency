package com.leetcode.concurrency;

import java.util.concurrent.Semaphore;

/**
 * 1226. The Dining Philosophers
 * https://leetcode.com/problems/the-dining-philosophers
 *
 * @author ARMAN
 */
public class DiningPhilosophers {

    private final Semaphore[] forks = {
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1)
    };

    private final Semaphore ownsRightFork = new Semaphore(4);

    public DiningPhilosophers() {

    }

    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {

        ownsRightFork.acquire();
        forks[philosopher].acquire();
        pickRightFork.run();

        forks[(philosopher + 1) % 5].acquire();
        pickLeftFork.run();

        eat.run();

        putRightFork.run();
        forks[philosopher].release();
        ownsRightFork.release();

        putLeftFork.run();
        forks[(philosopher + 1) % 5].release();
    }
}
