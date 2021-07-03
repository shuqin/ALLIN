package zzz.study.threadprogramming.basic;

import zzz.study.threadprogramming.basic.simulation.TimeIndicator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolatileDemo2 implements Runnable {

    private static int count = 1;

    private final int id;

    public VolatileDemo2(int id) {
        super();
        this.id = id;
    }

    public static void setZero() {
        count = 0;
    }

    public static void main(String[] args) {

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            es.execute(new VolatileDemo2(i));
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }

        VolatileDemo2.setZero();
        System.out.println(TimeIndicator.getcurrTime() + ":\t" + "count = " + count);
        System.out.println("quit from main");
    }

    public boolean isZero() {
        return count == 0;
    }

    public void run() {
        if (isZero()) {
            System.out.println(TimeIndicator.getcurrTime() + ":\t" + this + " : Zero, Zero, Zero");
        } else {
            System.out.println(TimeIndicator.getcurrTime() + ":\t" + this + " : " + (++count));
        }

    }

    public String toString() {
        return "Thread[" + id + "]";
    }


}
