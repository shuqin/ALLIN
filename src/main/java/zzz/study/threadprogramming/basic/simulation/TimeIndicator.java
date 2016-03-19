package zzz.study.threadprogramming.basic.simulation;

import java.util.concurrent.TimeUnit;

public class TimeIndicator {

    private static long START = System.nanoTime();

    /**
     * 获取当前时刻，精确到微秒
     */
    public static long getcurrTime() {
        long currTime = (System.nanoTime() / 1000) % 100000000;
        return currTime;
    }

    public static void showCurrTime() {
        System.out.println("当前时刻： " + getcurrTime());
    }

    /**
     * 计算流逝的时间，精确到微秒
     */
    public static long getEclipsed() {
        long interval = (System.nanoTime() - START) / 1000;
        return interval;
    }

    public static void main(String[] args) {
        // 从此时开始计时
        TimeIndicator.showCurrTime();

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println("Interrupted.");
        }

        TimeIndicator.showCurrTime();
        System.out.println("Elipsed: " + TimeIndicator.getEclipsed());

    }

}
