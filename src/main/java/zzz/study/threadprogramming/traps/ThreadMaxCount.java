package zzz.study.threadprogramming.traps;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadMaxCount {
  private static AtomicInteger count = new AtomicInteger();
  public static void main(String[] args) throws InterruptedException {
    while (true) {
      new Thread(() -> {
        try {
          TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
        }
      }).start();
      System.out.println("thread num:" + count.incrementAndGet());
      TimeUnit.MILLISECONDS.sleep(10);
    }
  }
}
