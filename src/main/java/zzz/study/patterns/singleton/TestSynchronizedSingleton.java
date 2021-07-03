package zzz.study.patterns.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 生成的是同一个工厂对象， 实现单例模式
 */
public class TestSynchronizedSingleton implements Runnable {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1000);

        for (int i = 0; i < 100000; i++) {
            es.submit(new TestSynchronizedSingleton());
        }

        try {
            TimeUnit.SECONDS.sleep(10);
            es.shutdown();
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    public void run() {
        SynchronizedSingletonWithLowPerformance synchronizedSingletonWithLowPerformance = SynchronizedSingletonWithLowPerformance.getInstance();
        System.out.println("run(): " + synchronizedSingletonWithLowPerformance);
    }
}
