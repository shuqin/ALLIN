package zzz.study.function.perspective;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CommonIF {

    public static <T, R> R ifElse(Predicate<T> cond, T t, Function<T, R> ifFunc, Supplier<R> defaultFunc) {
        return cond.test(t) ? ifFunc.apply(t) : defaultFunc.get();
    }

    public static <T, R> R ifElse(Predicate<T> cond, T t, Supplier<R> ifSupplier, Supplier<R> defaultSupplier) {
        return cond.test(t) ? ifSupplier.get() : defaultSupplier.get();
    }

    public static <T, R> Supplier<R> ifElseReturnSupplier(Predicate<T> cond, T t, Supplier<R> ifSupplier, Supplier<R> defaultSupplier) {
        return cond.test(t) ? ifSupplier : defaultSupplier;
    }

    public static <T> void ifThen(Predicate<T> cond, T t, Consumer<T> action) {
        if (cond.test(t)) {
            action.accept(t);
        }
    }

    public static <T> boolean alwaysTrue(T t) {
        return true;
    }
}
