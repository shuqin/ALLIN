package zzz.study.threadprogramming.traps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentCombinedOperation {

  private Map<String, AtomicLong> map = new ConcurrentHashMap<>();

  public long addAndGet(String key) {
    return map.getOrDefault(key, new AtomicLong()).getAndIncrement();
  }

  public long get(String key) {
    return map.get(key).get();
  }

  public static void main(String[] args) {
    ConcurrentCombinedOperation concurrentCombinedOperation = new ConcurrentCombinedOperation();
    ThreadStarter.startMultiThreads(
        (ti) -> {
          System.out.println(ti + ":" + concurrentCombinedOperation.addAndGet("key"));
        }
    );
    System.out.println("final: " + concurrentCombinedOperation.addAndGet("key"));
  }
}
