package zzz.study.threadprogramming.traps;

import java.util.HashMap;
import java.util.Map;

public class SychronizedOperation {

  private Map<String, Integer> map = new HashMap<>();

  public synchronized void add(String key) {
    Integer value = map.get(key);
    if(value == null) {
      map.put(key, 1);
    }
    else {
      map.put(key, value + 1);
    }
  }

  public synchronized Integer get(String key) {
    return map.get(key);
  }

  public static void main(String[] args) {
    SychronizedOperation sychronizedOperation = new SychronizedOperation();
    ThreadStarter.startMultiThreads(
        (ti) -> {
          sychronizedOperation.add("key");
          System.out.println(ti + ":" + sychronizedOperation.get("key"));
        }
    );

    System.out.println("final: " + sychronizedOperation.get("key"));
  }
}
