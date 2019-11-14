package zzz.study.threadprogramming.traps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentCombinedOperation {

  private Map<String, AtomicLong> map = new ConcurrentHashMap<>();

  private Lock lock = new ReentrantLock();

  public long addAndGet(String key) {
    if(lock.tryLock()) {
      try {
        map.putIfAbsent(key, new AtomicLong());
      } finally {
        lock.unlock();
      }
    }
    return map.get(key).incrementAndGet();
  }

  public long addAndGetEffective(String key) {
    init(key);
    return map.get(key).incrementAndGet();
  }

  private void init(String key) {
    if (map.get(key) == null) {
      synchronized (key) {
        if (map.get(key) == null) {
          map.put(key, new AtomicLong());
        }
      }
    }
  }

  public long get(String key) {
    return map.get(key).get();
  }

  public static void main(String[] args) throws InterruptedException {
    ConcurrentCombinedOperation concurrentCombinedOperation = new ConcurrentCombinedOperation();
    ThreadStarter.startMultiThreads(
        (ti) -> {
          System.out.println(ti + ":" + concurrentCombinedOperation.addAndGetEffective("key"));
        }
    );
    TimeUnit.SECONDS.sleep(3);
    System.out.println("final: " + concurrentCombinedOperation.get("key"));
  }
}
