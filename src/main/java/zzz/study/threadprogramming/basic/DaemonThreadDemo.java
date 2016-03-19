package zzz.study.threadprogramming.basic;

import zzz.study.threadprogramming.basic.dataconcurrency.PrimePrintSingleThread;

public class DaemonThreadDemo implements Runnable {

    public static void main(String[] args) {

        // 创建后台线程
        DaemonThreadDemo daemonThread = new DaemonThreadDemo();
        Thread t = new Thread(daemonThread);
        t.setDaemon(true);
        t.start();

        // 创建非后台线程
        new Thread(new Thread() {

            public void run() {
                new PrimePrintSingleThread(100).runAndPrint();
            }

        }).start();

        // Main 线程结束
        System.out.println("quit main.");
    }

    public void run() {
        double d = 1;
        for (int i = 0; i < 15000; i++) {
            d += (Math.PI + Math.E) / d;
            System.out.println("d = " + d);
        }
    }

}
