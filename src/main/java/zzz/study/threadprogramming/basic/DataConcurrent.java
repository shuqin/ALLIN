package zzz.study.threadprogramming.basic;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataConcurrent {

    /*
     * 判断 n 是否为质数
     */
    public static boolean f(int n) {
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {

        Executor exec = Executors.newCachedThreadPool();
        Runnable task1 = new Runnable() {

            public void run() {

                System.out.println("Thread1");
                for (int i = 2; i < 500; i++) {
                    System.out.println(i + ": " + f(i));
                }
            }
        };
        exec.execute(task1);

        Runnable task2 = new Runnable() {

            public void run() {

                System.out.println("Thread2");
                for (int i = 500; i < 1000; i++) {
                    System.out.println(i + ": " + f(i));
                }
            }
        };
        exec.execute(task2);
    }

}


/*
 * Note:
 * 多线程的两个用途：
 * 1. 将CPU计算与I/O操作相互隔离开来；
 * 2. 数据并行性计算任务，将大数据集计算分成多个子数据集计算任务
 *
 * 本例演示使用多线程完成数据并行计算任务
 *
 */
