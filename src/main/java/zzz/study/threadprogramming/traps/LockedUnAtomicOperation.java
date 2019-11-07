package zzz.study.threadprogramming.traps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedUnAtomicOperation {

  private Map<String, Integer> map = new HashMap<>();

  private Lock lock = new ReentrantLock();

  public int safeAddAndGet(String key) {
    try {
      lock.lockInterruptibly();
      Integer value = map.getOrDefault(key,1);
      map.put(key, value + 1);
      int newValue = map.get(key);
      return newValue;
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      return map.get(key);
    } finally {
      lock.unlock();
    }
  }

  public int get(String key) {
    return map.get(key);
  }

  public static void main(String[] args) throws InterruptedException {
    LockedUnAtomicOperation unatomicOperation = new LockedUnAtomicOperation();
    List<Thread> threadList = new ArrayList<>();
    for (int t=0; t<3; t++) {
      int finalT = t;
      Thread th = new Thread(() -> {
        for (int i=0; i<100000; i++) {
          int newVal = unatomicOperation.safeAddAndGet("key");
          System.out.println(finalT + ":" + newVal);
        }
      });
      threadList.add(th);
    }
    for (Thread t: threadList) {
      t.start();
    }
    for (Thread t: threadList) {
      t.join();
    }
    System.out.println("final: " + unatomicOperation.get("key"));
  }
}
