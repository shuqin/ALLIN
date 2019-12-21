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


    System.out.println("func sum:" + reduce(list, (x,y) -> x+y, () -> 0));
    System.out.println("func multiply: " + reduce(list, (x,y) -> x*y, () -> 1));

  }

  public static <E,T> T reduce(List<E> list, BiFunction<E,T,T> biFunc, Supplier<T> init) {
    T result = init.get();
    for (E e: list) {
      result = biFunc.apply(e, result);
    }
    return result;
  }
}
