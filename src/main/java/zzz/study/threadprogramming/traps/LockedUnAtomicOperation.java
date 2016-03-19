package zzz.study.threadprogramming.traps;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedUnAtomicOperation {

    private Map<String, Integer> map = new HashMap<>();

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        LockedUnAtomicOperation lockedUnAtomicOperation = new LockedUnAtomicOperation();
        ThreadStarter.startMultiThreads(
                (ti) -> {
                    int newVal = lockedUnAtomicOperation.safeAddAndGet("key");
                    System.out.println(ti + ":" + newVal);
                }
        );
        System.out.println("final: " + lockedUnAtomicOperation.get("key"));
    }

    public int safeAddAndGet(String key) {
        lock.tryLock();
        try {
            Integer value = map.getOrDefault(key, 1);
            map.put(key, value + 1);
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }

    public int get(String key) {
        return map.get(key);
    }
}
