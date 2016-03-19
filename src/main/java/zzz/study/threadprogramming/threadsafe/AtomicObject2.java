package zzz.study.threadprogramming.threadsafe;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicObject2 {

    private AtomicInteger count;

    public AtomicObject2() {
        this.count = new AtomicInteger(0);
    }

    public AtomicObject2(AtomicInteger count) {
        this.count = count;
    }

    public int increAndGetCount() {
        return count.incrementAndGet();
    }

    public void increCount() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }

}
