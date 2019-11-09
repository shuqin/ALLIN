package zzz.study.threadprogramming.traps;

public class ThreadLocalTraps {

  private ThreadLocal<Integer> context = new ThreadLocal<>();

  public void setContext(Integer value) {
    context.set(++value);
  }

  public Integer getContext() {
    return context.get();
  }

  public static void main(String[] args) {
    ThreadLocalTraps t = new ThreadLocalTraps();
    for (int i=0; i <=100; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          t.setContext(1);
          System.out.println("context: " + t.getContext());
        }
      }).start();
    }
  }
}
