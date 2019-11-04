package zzz.study.threadprogramming.traps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnatomicOperation {

  private Map<String, Integer> map = new ConcurrentHashMap<>();

  public void add(String key) {
    Integer value = map.get(key);
    if(value == null) {
      map.put(key, 1);
    }
    else {
      map.put(key, value + 1);
    }
  }

  public void nonSafeAdd(String key) {
    map.put(key, map.putIfAbsent(key, 1)+1);
  }

  public Integer get(String key) {
    return map.get(key);
  }

  public static void main(String[] args) throws InterruptedException {
    UnatomicOperation unatomicOperation = new UnatomicOperation();
    List<Thread> threadList = new ArrayList<>();
    for (int t=0; t<3; t++) {
      int finalT = t;
      Thread th = new Thread(() -> {
        for (int i=0; i<100000; i++) {
          unatomicOperation.nonSafeAdd("key");
          System.out.println(finalT + ":" + unatomicOperation.get("key"));
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
