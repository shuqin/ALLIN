package zzz.study.threadprogramming.basic;

import zzz.study.threadprogramming.basic.experiment.ConcurrentExperiment;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by lovesqcc on 16-2-15.
 * <p>
 * 演示 AtomicLong 用法, 实现一个积分增加器:
 * 两个线程, 对同一个积分增加, 每次回复增加一, 每次发帖增加十
 */
public class AtomicLongDemo implements ConcurrentExperiment {

    private static final int INIT_VALUE = 10;
    private static final int COUNTS = 10000;
    private static final int FINAL_RESULT = INIT_VALUE + COUNTS * 11;

    public boolean perform() {
        final AtomicLong points = new AtomicLong(INIT_VALUE);
        final CountDownLatch gate = new CountDownLatch(2);

        Thread ifReply = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < COUNTS; i++) {
                    points.getAndIncrement();
                }
                gate.countDown();
            }
        });

        Thread ifPost = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < COUNTS; i++) {
                    points.getAndAdd(10);
                }
                gate.countDown();
            }
        });

        ifReply.start();
        ifPost.start();
        try {
            gate.await();

            // after adding, validate the result
            return points.get() == FINAL_RESULT;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
