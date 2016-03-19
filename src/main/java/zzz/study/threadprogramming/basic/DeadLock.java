package zzz.study.threadprogramming.basic;

import java.util.concurrent.TimeUnit;

public class DeadLock {

    private static Object res1 = new Object();

    private static Object res2 = new Object();

    public static void main(String[] args) {
        new DeadLock().deadlock();
    }

    public void deadlock() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (res1) {

                    System.out.println("t1 obtain res1");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        // Interrupted
                    }

                    synchronized (res2) {
                        // do something;
                        System.out.println("t1 obtain res2");
                    }
                }


            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (res2) {

                    System.out.println("t2 obtain res2");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        // Interrupted
                    }

                    synchronized (res1) {
                        System.out.println("t2 obtain res1");
                    }
                }
            }
        }).start();
    }


}
