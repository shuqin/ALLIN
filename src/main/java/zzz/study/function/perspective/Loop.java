package zzz.study.function.perspective;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class Loop {

  public static void main(String[]args) {
    Integer sum = 0;
    List<Integer> list = Arrays.asList(1,2,3,4,5);
    for (int i=0; i < list.size(); i++) {
      sum += list.get(i);
    }
    System.out.println(sum);

    Integer multiply = 1;
    for (int i=0; i < list.size(); i++) {
      multiply *= list.get(i);
    }
    System.out.println(multiply);


    System.out.println("func sum:" + loop(list, (x,y) -> x+y, () -> 0));
    System.out.println("func multiply: " + loop(list, (x,y) -> x*y, () -> 1));

  }

  public static <T> T loop(List<T> list, BinaryOperator<T> bifunc, Supplier<T> init) {
    return list.stream().reduce(init.get(), bifunc);
  }
}
