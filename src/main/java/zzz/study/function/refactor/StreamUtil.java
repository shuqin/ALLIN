package zzz.study.function.refactor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by shuqin on 17/6/24.
 */
public class StreamUtil {

    public static <T, R> Set<R> mapToSet(List<T> data, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Sets.newHashSet();
        }

        return data.stream().map(mapFunc).collect(Collectors.toSet());
    }

    public static <T, R> List<R> map(List<T> data, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayList();
        }

        return data.stream().map(mapFunc).collect(Collectors.toList());
    }

    public static <T, R> List<T> filter(List<T> data, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayList();
        }

        return data.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T, R> long filterCount(List<T> data, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        }

        return data.stream().filter(predicate).count();
    }

    public static <T, R> List<T> sort(List<T> data, Comparator<? super T> comparator) {
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayList();
        }

        return data.stream().sorted(comparator).collect(Collectors.toList());
    }

    public static <T, K> Map<K, List<T>> group(List<T> data, Function<T,K> keyFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>();
        }
        return data.stream().collect(Collectors.groupingBy(keyFunc));
    }

    public static <T,K,V> Map<K, List<V>> group(List<T> data, Function<T,K> keyFunc, Function<T, V> valueFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>();
        }
        return data.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, toList())));
    }

    public static <T, K> Map<K,T> toMap(List<T> data, Function<T,K> keyFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>();
        }
        return data.stream().collect(Collectors.toMap(keyFunc, Function.identity(), (v1,v2) -> v1));
    }

    public static <T, K,V> Map<K, V> toMap(List<T> origin, Function<T, K> keyFunc, Function<T,V> valueFunc) {
        if (CollectionUtils.isEmpty(origin)) {
            return new HashMap<>();
        }
        return origin.stream().collect(Collectors.toMap(keyFunc, valueFunc));
    }

}
