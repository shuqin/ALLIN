package zzz.study.function.perspective;

import java.util.function.Supplier;

public class Assignment {

  public static void main(String[]args) {
    int i = 100;
    System.out.println(i);

    Supplier<Integer> f = () -> 100;
    System.out.println(f.get());
  }
}
