package zzz.study.function.refactor;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by shuqin on 17/6/24.
 */
public class StreamUtil {

  public static <T,R> List<R> map(List<T> data, Function<T, R> mapFunc) {
    return data.stream().map(mapFunc).collect(Collectors.toList());

  }

  public static <T> List<T> filter(List<T> data, Predicate<T> filterFunc) {
    return data.stream().filter(filterFunc).collect(Collectors.toList());

  }

  public static <T,R> List<R> filterAndMap(List<T> data, Predicate<T> filterFunc, Function<T, R> mapFunc) {
    return data.stream().filter(filterFunc).map(mapFunc).collect(Collectors.toList());

  }

}
