package zzz.study.function.perspective;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
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

    System.out.println("func sum with generic:" + loopWithGeneric(list, (x,y) -> x+y, () -> 0));
    System.out.println("func multiply with generic: " + loopWithGeneric(list, (x,y) -> x*y, () -> 1));

  }

  public static Integer loop(List<Integer> list, BiFunction<Integer,Integer,Integer> bifunc, Supplier<Integer> supplier) {
    Integer result = supplier.get();
    for (int i=0; i < list.size(); i++) {
      result = bifunc.apply(result, list.get(i));
    }
    return result;
  }

  public static <T> T loopWithGeneric(List<T> list, BiFunction<T,T,T> bifunc, Supplier<T> init) {
    T result = init.get();
    for (int i=0; i < list.size(); i++) {
      result = bifunc.apply(result, list.get(i));
    }
    return result;
  }
}
