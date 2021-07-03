/**
 * TestThread ：
 * <p>
 * 使用主线程不断从键盘缓冲区获取输入，写入自创建的字符缓冲区，并显示缓冲区内容；
 * 使用一个子线程不断从自创建的字符缓冲区取出字符输出，并显示缓冲区内容；
 */

package zzz.study.threadprogramming.basic.simulation.usequeue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ProducerConsumerProblem {

    public static void main(String[] args) {

        int num = 10;

        System.out.println(" ---- Thread main starts up ---- ");

        BlockingQueue<Character> queue = new ArrayBlockingQueue<Character>(15);
        ExecutorService es = Executors.newCachedThreadPool();

        List<ProducerUsingQueue> producers = new ArrayList<ProducerUsingQueue>();
        List<ConsumerUsingQueue> comsumers = new ArrayList<ConsumerUsingQueue>();

        for (int i = 0; i < num; i++) {
            producers.add(new ProducerUsingQueue(i, queue));
            comsumers.add(new ConsumerUsingQueue(i, queue));
        }

        for (int i = 0; i < num; i++) {
            es.execute(producers.get(i));
            es.execute(comsumers.get(i));
        }
        es.shutdown();
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProducerUsingQueue.cancel();
        ConsumerUsingQueue.cancel();
        es.shutdownNow();

        System.out.println("Time to be over.");

    }

}
