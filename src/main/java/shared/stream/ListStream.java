package shared.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @Description 列表的 Stream 包装
 * @Date 2021/5/16 7:51 上午
 * @Created by qinshu
 */
public class ListStream<T> {

    private List<T> origin;

    public ListStream(List<T> list) {
        if (list == null) {
            this.origin = new ArrayList<>();
        } else {
            this.origin = list;
        }
    }

    public static <T> ListStream<T> stream(List<T> list) {
        return new ListStream<>(list);
    }

    public <R> List<R> map(Function<? super T, R> func) {
        return origin.stream().map(func).collect(toList());
    }

    public <R> Set<R> mapToSet(Function<? super T, R> func) {
        return origin.stream().map(func).collect(Collectors.toSet());
    }

    public List<T> filter(Predicate<? super T> predicate) {
        return origin.stream().filter(predicate).collect(toList());
    }

    public <R> List<R> filterAndMapChain(List<Predicate<? super T>> beforeFilters,
                                         Function<? super T, R> mapFunc, Predicate<R>... afterFilters) {
        Stream<T> stream = origin.stream();
        Stream<R> midResult = null;
        if (beforeFilters != null) {
            for (Predicate f : beforeFilters) {
                stream = stream.filter(f);
            }
        }
        if (mapFunc != null) {
            midResult = stream.map(mapFunc);
        }
        if (afterFilters != null) {
            for (Predicate<R> f : afterFilters) {
                midResult = midResult.filter(f);
            }
        }
        return midResult.collect(toList());
    }

    public <K> Map<K, List<T>> group(Function<T, K> keyFunc) {
        return origin.stream().collect(Collectors.groupingBy(keyFunc));
    }

    public <K, V> Map<K, List<V>> group(Function<T, K> keyFunc, Function<T, V> valueFunc) {
        return origin.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, toList())));
    }

    public <K> Map<K, T> toMap(Function<T, K> keyFunc) {
        return origin.stream().collect(Collectors.toMap(keyFunc, Function.identity(), (v1, v2) -> v1));
    }

    public <K,V> Map<K, V> toMap(Function<T, K> keyFunc, Function<T,V> valueFunc) {
        return origin.stream().collect(Collectors.toMap(keyFunc, valueFunc));
    }

}
