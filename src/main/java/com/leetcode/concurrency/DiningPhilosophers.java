package com.leetcode.concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 阿尔曼
 */
public class DiningPhilosophers {

    private final Semaphore[] forks = {
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1)
    };

    public DiningPhilosophers() {

    }

    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {

        forks[philosopher].acquire();
        pickRightFork.run();

        forks[philosopher == 4 ? 0 : philosopher + 1].acquire();
        pickLeftFork.run();

        eat.run();

        putRightFork.run();
        forks[philosopher].release();

        putLeftFork.run();
        forks[philosopher == 4 ? 0 : philosopher + 1].release();
    }
}
