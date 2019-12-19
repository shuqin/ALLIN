package zzz.study.function.perspective;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Condition {

  public static void main(String[]args) {
    System.out.println(String.format("%d, %d, %d", plainIfElse(8), plainIfElse(-10), plainIfElse(0)));

    System.out.println(String.format("%d, %d, %d", mapFunc(8), mapFunc(-10), mapFunc(0)));

  }

  public static Integer plainIfElse(int x) {
    if (x > 0) {
      return 1;
    }
    else if (x < 0) {
      return -1;
    }
    else {
      return 0;
    }
  }

  public static boolean ifPositive(int x) {
    return x > 0;
  }

  public static boolean ifNegative(int x) {
    return x < 0;
  }

  public static boolean ifZero(int x) {
    return x == 0;
  }


  public static Integer mapFunc(int x) {
    Map<Predicate<Integer>, Supplier<Integer>> condMap = new HashMap<>();
    condMap.put(Condition::ifPositive, () -> 1);
    condMap.put(Condition::ifNegative, () -> -1);
    condMap.put(Condition::ifZero, () -> 0);
    return travel(condMap, x, () -> 0);
  }

  public static Integer travel(Map<Predicate<Integer>, Supplier<Integer>> map, Integer x, Supplier<Integer> defaultSupplier) {
    final Integer[] result = new Integer[] { defaultSupplier.get() };

    map.forEach(
        (keyFunc, valueFunc) -> {
          if (keyFunc.test(x)) {
            result[0] = valueFunc.get();
          }
        }
    );
    return result[0];
  }


}
