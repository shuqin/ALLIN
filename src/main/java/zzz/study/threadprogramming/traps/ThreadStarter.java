package zzz.study.threadprogramming.traps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class ThreadStarter {

    public static void startMultiThreads(Consumer<Integer> consumer) {

        try {
            startMultiThreadsV2(3, 100000, consumer);
        } catch (InterruptedException e) {
            System.err.println("error: " + e.getMessage());
        }
    }

    public static void startMultiThreads(int threadNum, int times, Consumer<Integer> consumer) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int t = 0; t < threadNum; t++) {
            int threadIndex = t;
            Thread th = new Thread(() -> {
                for (int i = 0; i < times; i++) {
                    consumer.accept(threadIndex);
                }
            });
            threadList.add(th);
            th.start();
        }
        for (Thread t : threadList) {
            t.join();
        }
    }

    public static void startMultiThreadsV2(int threadNum, int times, Consumer<Integer> consumer) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            new Thread(
                    new Worker(countDownLatch, consumer, i, times), "t" + i
            ).start();
        }
        countDownLatch.await();
    }

    static class Worker implements Runnable {

        private CountDownLatch countDownLatch;
        private Consumer consumer;
        private int threadIndex;
        private int times;

        public Worker(CountDownLatch countDownLatch, Consumer consumer,
                      int threadIndex, int times) {
            this.countDownLatch = countDownLatch;
            this.consumer = consumer;
            this.threadIndex = threadIndex;
            this.times = times;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                consumer.accept(threadIndex);
            }
            countDownLatch.countDown();
        }
    }
}
