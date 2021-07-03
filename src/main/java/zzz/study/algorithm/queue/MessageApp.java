package zzz.study.algorithm.queue;

import zzz.study.utils.MessageUtil;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MessageApp {

    static ExecutorService executor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new ArrayBlockingQueue(1024));

    static ScheduledThreadPoolExecutor scheduleExecutor = new ScheduledThreadPoolExecutor(3);

    public static void main(String[] args) {

        BatchGetQueue<Message> batchGetQueue = new BatchGetQueue();
        Long start = System.currentTimeMillis();

        executor.submit(
                () -> {
                    while (System.currentTimeMillis() - start <= 180000) {
                        batchGetQueue.offer(MessageUtil.build());
                        System.out.println("produce: " + batchGetQueue);
                        try {
                            TimeUnit.MILLISECONDS.sleep(50L);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e.getCause());
                        }
                    }
                }
        );

        scheduleExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<Message> messages = batchGetQueue.batchPoll(30);
                System.out.println("consumer:" + messages.stream().map(Message::getStatus).collect(Collectors.toList()));
            }
        }, 1, 1, TimeUnit.SECONDS);


    }

}
