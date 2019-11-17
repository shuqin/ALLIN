package zzz.study.threadprogramming.traps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalLeak {

  private ThreadLocal<Integer> context = new ThreadLocal();

  public ThreadLocalLeak(Integer initValue) {
    context.set(initValue);
  }

  public Integer get() {
    return context.get();
  }

  public void set(Integer initValue) {
    context.set(initValue);
  }

  public void clear() {
    context.remove();
  }

  public static void main(String[] args) throws InterruptedException {
    ThreadLocalLeak threadLocalLeak = new ThreadLocalLeak(5);
    ExecutorService executor = Executors.newFixedThreadPool(10);

    executor.execute(
        () -> {
          for (int i=0; i<=100000; i++) {
            System.out.println(System.nanoTime() + " set before:" + Thread.currentThread() + ": " +  threadLocalLeak.get());
            threadLocalLeak.set(i);
            System.out.println(System.nanoTime() + " set after:" + Thread.currentThread() + ": " +  threadLocalLeak.get());
            //threadLocalLeak.clear();
          }
        }
    );

    executor.shutdown();
    executor.awaitTermination(3000, TimeUnit.SECONDS);

  }


}
