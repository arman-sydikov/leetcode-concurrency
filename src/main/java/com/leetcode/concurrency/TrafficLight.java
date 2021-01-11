package com.leetcode.concurrency;

/**
 * 1279. Traffic Light Controlled Intersection
 * https://leetcode.com/problems/traffic-light-controlled-intersection
 *
 * @author ARMAN
 */
public class TrafficLight {

    private int greenRoadId = 1;

    public TrafficLight() {

    }

    public void carArrived(
            int carId,           // ID of the car
            int roadId,          // ID of the road the car travels on. Can be 1 (road A) or 2 (road B)
            int direction,       // Direction of the car
            Runnable turnGreen,  // Use turnGreen.run() to turn light to green on current road
            Runnable crossCar    // Use crossCar.run() to make car cross the intersection
    ) {
        synchronized (this) {
            if (greenRoadId != roadId) {
                turnGreen.run();
                greenRoadId = roadId;
            }
            crossCar.run();
        }
    }
}
