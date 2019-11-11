package zzz.study.threadprogramming.traps;

import java.util.ArrayList;
import java.util.Collections;
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

  public List<Integer> getImmutableNums() {
    return Collections.unmodifiableList(nums);
  }

  static class EscapedObjectTester {
    public static void main(String[] args) throws InterruptedException {
      EscapedObject escapedObject = new EscapedObject();
      escapedObject.add(5);

      /*
      List<Integer> escaped = escapedObject.getNums();
      ThreadStarter.startMultiThreads(10, 3,
          (ti) -> {
            escaped.add(ti*ti);
            System.out.println(ti + ":" + escaped);
          }
      );
      */

      List<Integer> escaped = escapedObject.getImmutableNums();
      ThreadStarter.startMultiThreads(10, 3,
          (ti) -> {
            Integer i = escaped.get(0);
            i = i.intValue() + 1;
            System.out.println(ti + ":" + i);
          }
      );

    }
  }
}
