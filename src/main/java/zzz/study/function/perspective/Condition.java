package zzz.study.function.perspective;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Condition {

    public static void main(String[] args) {
        System.out.println(String.format("%d, %d, %d", plainIfElse(8), plainIfElse(-10), plainIfElse(0)));

        System.out.println(String.format("%d, %d, %d", mapFunc(8).get(), mapFunc(-10).get(), mapFunc(0).get()));

        System.out.println(String.format("%d, %d, %d", ifElseWithFunctional(8).get(), ifElseWithFunctional(-10).get(), ifElseWithFunctional(0).get()));

    }

    public static Integer plainIfElse(int x) {
        if (x > 0) {
            return 1;
        } else if (x < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public static Supplier<Integer> ifElseWithFunctional(int x) {
        return CommonIF.ifElseReturnSupplier(Condition::ifPositive, x,
                Condition::positiveUnit,
                CommonIF.ifElseReturnSupplier(Condition::ifNegative, x, Condition::negativeUnit, Condition::zero));
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

    public static Integer positiveUnit() {
        return 1;
    }

    public static Integer negativeUnit() {
        return -1;
    }

    public static Integer zero() {
        return 0;
    }


    public static Supplier<Integer> mapFunc(int x) {
        Map<Predicate<Integer>, Supplier<Integer>> condMap = new HashMap<>();
        condMap.put(Condition::ifPositive, Condition::positiveUnit);
        condMap.put(Condition::ifNegative, Condition::negativeUnit);
        condMap.put(Condition::ifZero, Condition::zero);
        return travelWithGeneric(condMap, x);
    }

    public static Supplier<Integer> travel(Map<Predicate<Integer>, Supplier<Integer>> map, Integer x) {
        return map.entrySet().stream().filter((k) -> k.getKey().test(x)).findFirst().map((k) -> k.getValue()).get();
    }

    public static <T, R> Supplier<R> travelWithGeneric(Map<Predicate<T>, Supplier<R>> map, T x) {
        return map.entrySet().stream().filter((k) -> k.getKey().test(x)).findFirst().map((k) -> k.getValue()).get();
    }

}
