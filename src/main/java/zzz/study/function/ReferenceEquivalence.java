package zzz.study.function;

/**
 * Created by shuqin on 17/12/31.
 */
public class ReferenceEquivalence {
  public static void main(String[] args) {
    Counting counting = new Counting(0);
    int result1 = counting.inc(5) + counting.inc(5);
    System.out.println("result = " + result1);

    Counting counting2 = new Counting(0);
    int result2 = 5 + counting.inc(5);
    System.out.println("result2 = " + result2);

    Counting counting3 = new Counting(0);
    int result3 = counting.incInner(10,5) + counting.incInner(10, 5);
    System.out.println("result3 = " + result3);

    Counting counting4 = new Counting(0);
    int result4 = 15 + counting.incInner(10, 5);
    System.out.println("result4 = " + result4);
  }

}

class Counting {
  private int count;
  public Counting(int count) {
    this.count = count;
  }

  public int inc(int n) {
    this.count += n;
    return this.count;
  }

  public int incInner(int count, int n) {
    return count + n;
  }

}