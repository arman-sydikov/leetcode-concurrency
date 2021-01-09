package com.leetcode;

import com.leetcode.concurrency.*;

/**
 * @author 阿尔曼
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class Demo {


    public static void main(String[] args) {
//        testFoo();
//        testFooBar();
//        testZeroEvenOdd(10);
//        testFizzBuzz(100);
//        testH2O("HHHHHHHOOOOOOOHHHHHHH");
//        testDiningPhilosophers(1);
    }

    private static void testFoo() {
        Foo foo = new Foo();

        new Thread(() -> {
            try {
                foo.first(() -> System.out.println("first"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                foo.second(() -> System.out.println("second"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                foo.third(() -> System.out.println("third"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void testFooBar() {
        FooBar fooBar = new FooBar(2);

        new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void testZeroEvenOdd(int n) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(n);

        new Thread(() -> {
            try {
                zeroEvenOdd.zero(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                zeroEvenOdd.odd(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                zeroEvenOdd.even(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void testFizzBuzz(int n) {
        FizzBuzz fizzBuzz = new FizzBuzz(n);

        new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.println("fizz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.println("buzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.println("fizzbuzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.number(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void testH2O(String s) {
        if (s == null) {
            return;
        }
        H2O h2o = new H2O();

        for (int i=0; i<s.length(); i++) {
            if ('H' == s.charAt(i)) {
                new Thread(() -> {
                    try {
                        h2o.hydrogen(() -> System.out.print("H"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                new Thread(() -> {
                    try {
                        h2o.oxygen(() -> System.out.print("O"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }

    private static void testDiningPhilosophers(int n) {
        DiningPhilosophers philosophers = new DiningPhilosophers();
        Runnable pickLeftFork = () -> System.out.println("Pick left fork");
        Runnable pickRightFork = () -> System.out.println("Pick right fork");
        Runnable eat = () -> System.out.println("Eat");
        Runnable putLeftFork = () -> System.out.println("Put left fork");
        Runnable putRightFork = () -> System.out.println("Put right fork");

        for (int id=0; id<5; id++) {
            new Thread(() -> {
                try {
                    for (int i=0; i<n; i++) {
                        philosophers.wantsToEat(0, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
