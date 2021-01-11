package com.leetcode.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author ARMAN
 */
class TrafficLightTest {

    /**
     * Class we want to test
     */
    private TrafficLight trafficLight;

    /**
     * StringBuffer
     */
    private StringBuffer sb;

    /**
     * CountDownLatch
     */
    private CountDownLatch latch;

    private ExecutorService service;

    private final char A = 'A';

    private final char B = 'B';

    @BeforeEach
    void setUp() {
        trafficLight = new TrafficLight();
        sb = new StringBuffer();
        service = Executors.newFixedThreadPool(2);
        latch = new CountDownLatch(5);
    }

    @Test
    void firstTest() throws InterruptedException, ExecutionException {

        int[] cars = {1,3,5,2,4};
        int[] directions = {2,1,2,4,3};

        for (int i=0; i<5; i++) {
            int car = cars[i];
            int direction = directions[i];
            if (directions[i] < 3) {
                // Road A
                service.execute(() -> {
                    trafficLight.carArrived(car,1, direction, new TurnGreen(car, A, direction), new CrossCar(A));
                    latch.countDown();
                });
            } else {
                // Road
                service.execute(() -> {
                    trafficLight.carArrived(car, 2, direction, new TurnGreen(car, B, direction), new CrossCar(B));
                    latch.countDown();
                });
            }
        }

        // Wait until the latch has counted down to zero
        latch.await();

        fail();
    }

    private static class TurnGreen implements Runnable {

        private final int carId;
        private final char road;
        private final int direction;

        private TurnGreen(int carId, char road, int direction) {
            this.carId = carId;
            this.road = road;
            this.direction = direction;
        }

        @Override
        public void run() {
            System.out.println("Car " + carId + " Has Passed Road " + road + " In Direction " + direction);
        }
    }

    private static class CrossCar implements Runnable {

        private final char road;

        private CrossCar(char road) {
            this.road = road;
        }

        @Override
        public void run() {
            System.out.println("Traffic Light On Road " + road + " Is Green");
        }
    }
}