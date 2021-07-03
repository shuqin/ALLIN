package zzz.study.patterns.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * 生成两个不同的全局单例， 无法实现单例模式
 */
public class SingletonInMultiThread implements Runnable {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(1000);

        for (int i = 0; i < 100000; i++) {
            es.submit(new SingletonInMultiThread());
        }

        try {
            TimeUnit.SECONDS.sleep(10);
            es.shutdown();
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }

    }

    public void run() {
        Singleton3 singleton3 = Singleton3.getInstance();
        System.out.println("run(): " + singleton3);
    }
}
