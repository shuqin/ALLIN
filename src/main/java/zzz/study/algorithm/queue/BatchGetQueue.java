package zzz.study.algorithm.queue;

import com.google.common.collect.Queues;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BatchGetQueue<E> extends LinkedBlockingQueue {

    private static final Integer BATCH_SIZE = 50;
    private BlockingQueue<E> queue = new LinkedBlockingQueue<>();

    public List<E> batchPoll(int batchSize) {
        try {
            List<E> elems = new ArrayList<>(batchSize);
            Queues.drain(this, elems, batchSize, 5, TimeUnit.SECONDS);
            return elems;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getCause());
        }

    }
}
