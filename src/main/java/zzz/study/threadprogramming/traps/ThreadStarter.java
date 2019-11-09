package zzz.study.threadprogramming.traps;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ThreadStarter {

  public static void startMultiThreads(Consumer<Integer> consumer) {

    try {
      startMultiThreads(3, 100000, consumer);
    } catch (InterruptedException e) {
      System.err.println("error: " + e.getMessage());
    }
  }

  public static void startMultiThreads(int threadNum, int times, Consumer<Integer> consumer) throws InterruptedException {
    List<Thread> threadList = new ArrayList<>();
    for (int t=0; t < threadNum; t++) {
      int threadIndex = t;
      Thread th = new Thread(() -> {
        for (int i=0; i < times; i++) {
          consumer.accept(threadIndex);
        }
      });
      threadList.add(th);
      th.start();
    }
    for (Thread t: threadList) {
      t.join();
    }
  }
}
