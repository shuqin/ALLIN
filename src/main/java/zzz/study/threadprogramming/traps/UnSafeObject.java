package zzz.study.threadprogramming.traps;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Setter
@Getter
public class UnSafeObject {

  private int i = 0;

  public static void main(String[] args) {
    UnSafeObject unSafeObject = new UnSafeObject();
    for (int t=0; t<5; t++) {
      int finalT = t;
      new Thread(() -> {
        for (int i= 1; i < 1000000; i++) {
          unSafeObject.setI(finalT);
          try {
            TimeUnit.MILLISECONDS.sleep(200);
          } catch (InterruptedException e) {
          }
          System.out.println("Thread" + finalT + ":" + unSafeObject.getI());
        }
      }).start();
    }
  }
}


