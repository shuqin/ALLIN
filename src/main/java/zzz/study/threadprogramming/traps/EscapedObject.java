package zzz.study.threadprogramming.traps;

import java.util.ArrayList;
import java.util.List;

public class EscapedObject {

  private List<Integer> nums = new ArrayList<>();

  private synchronized void add(Integer num) {
    if (num != null) {
      nums.add(num);
    }
  }

  public List<Integer> getNums() {
    return nums;
  }

  static class EscapedObjectTester {
    public static void main(String[] args) throws InterruptedException {
      EscapedObject escapedObject = new EscapedObject();
      escapedObject.add(5);
      List<Integer> escaped = escapedObject.getNums();
      ThreadStarter.startMultiThreads(3, 3,
          (ti) -> {
            escaped.add(ti*ti);
            System.out.println(ti + ":" + escaped);
          }
      );
    }
  }
}
