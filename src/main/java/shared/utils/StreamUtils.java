package shared.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

public class StreamUtils {

    public static <T, R> Set<R> mapToSet(List<T> data, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptySet();
        }

        return data.stream().map(mapFunc).collect(Collectors.toSet());
    }

    public static <T, R> Set<R> mapToSet(Set<T> data, Function<T, R> mapFunc) {
        return mapToSet(Lists.newArrayList(data), mapFunc);
    }

    public static <T, R> Set<R> mapToSet(List<T> data, Predicate<? super T> filterFunc, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptySet();
        }
        return data.stream().filter(filterFunc).map(mapFunc).collect(Collectors.toSet());
    }

    public static <T, R> Set<R> mapToNonNullSet(List<T> data, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptySet();
        }
        return data.stream().filter(Objects::nonNull).map(mapFunc).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public static <T, R> List<R> map(List<T> data, Function<T, R> mapFunc) {
        return mapNonNull(data, mapFunc);
    }

    /**
     * 将data通过mapFunc函数，生成新的List
     *
     * @param defaultData data为空时，返回的默认数据
     */
    public static <T, R> List<R> map(List<T> data, Function<T, R> mapFunc, List<R> defaultData) {
        if (CollectionUtils.isEmpty(data)) {
            return defaultData;
        }
        return mapNonNull(data, mapFunc);
    }

    public static <T, R> List<R> mapNonNull(List<T> data, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }

        return data.stream().map(mapFunc).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T, R> List<R> mapDistinct(List<T> data, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }

        return data.stream().map(mapFunc).distinct().collect(Collectors.toList());
    }

    public static <T, R> List<R> mapDistinct(List<T> data, Function<T, R> mapFunc, Predicate<? super R> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }

        return data.stream().map(mapFunc).filter(predicate).distinct().collect(Collectors.toList());
    }

    public static <T, R> List<R> mapToList(List<T> data, Function<T, List<R>> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.stream()
                .flatMap(mapFunc.andThen(List::stream))
                .distinct()
                .collect(Collectors.toList()); }

    public static <T, R extends Comparable<? super R>> List<T> listDistinct(List<T> data, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(mapFunc))), ArrayList::new));
    }


    public static <T> List<T> filter(List<T> data, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }

        return data.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> Set<T> filter(Set<T> data, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptySet();
        }

        return data.stream().filter(predicate).collect(Collectors.toSet());
    }

    public static <T> long filterCount(List<T> data, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        }

        return data.stream().filter(predicate).count();
    }

    public static <T, R> List<R> filterAndMap(List<T> data, Predicate<? super T> predicate, Function<T, R> mapFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.stream().filter(predicate).map(mapFunc).collect(toList());
    }

    public static <T, R> List<R> mapAndFilter(List<T> data, Function<T, R> mapFunc, Predicate<? super R> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().map(mapFunc).filter(predicate).collect(toList());
    }

    public static <T> T findFirst(List<T> data, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return findFirst(data, predicate, null);
    }

    public static <T> T findFirst(List<T> data, Predicate<? super T> predicate, T defaultValue) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().filter(predicate).findFirst().orElse(defaultValue);
    }

    public static <T> List<T> sort(List<T> data, Comparator<? super T> comparator) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }

        return data.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 最多只返回 maxSize 条数据
     */
    public static <T> List<T> limit(List<T> data, long maxSize) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.stream().limit(maxSize).collect(Collectors.toList());
    }

    public static <T, K> Map<K, List<T>> group(List<T> data, Function<T, K> keyFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.groupingBy(keyFunc));
    }

    public static <T, K, V> Map<K, List<V>> group(List<T> data, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, toList())));
    }

    public static <T, K, V> Map<K, List<V>> groupToList(List<T> data, Function<T, K> keyFunc, Function<T, List<V>> valueFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream()
                .collect(Collectors.groupingBy(
                        keyFunc,
                        Collectors.mapping(valueFunc,toList())
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .flatMap(List::stream)
                                .distinct()
                                .collect(Collectors.toList())
                ));
    }

    public static <T, K> Map<K, T> toMap(List<T> data, Function<T, K> keyFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.toMap(keyFunc, Function.identity(), (v1, v2) -> v1));
    }

    public static <T, K, V> Map<K, V> toMap(List<T> origin, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollectionUtils.isEmpty(origin)) {
            return new HashMap<>(0);
        }
        return origin.stream().collect(Collectors.toMap(keyFunc, valueFunc, (v1, v2) -> v1));
    }

    public static <T, K> Map<K, List<T>> groupingBy(List<T> origin, Function<T, K> keyFunc) {
        if (CollectionUtils.isEmpty(origin)) {
            return new HashMap<>(0);
        }
        return origin.stream().collect(Collectors.groupingBy(keyFunc));
    }

    public static <T, K, V> Map<K, Set<V>> toMapValueSet(List<T> origin, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollectionUtils.isEmpty(origin)) {
            return new HashMap<>(0);
        }
        return origin.stream().collect(Collectors.toMap(
                keyFunc, v -> Sets.newHashSet(valueFunc.apply(v)), (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                }));
    }

    public static <T, R> List<R> flatMap(List<T> data, Function<T, List<R>> listFunc) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.stream().flatMap(d -> listFunc.apply(d).stream()).collect(Collectors.toList());
    }
}
