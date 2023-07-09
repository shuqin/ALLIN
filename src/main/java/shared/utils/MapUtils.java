package shared.utils;

import com.google.common.collect.Sets;
import org.apache.commons.compress.utils.Lists;
import shared.constants.JavaCodeGuideConstants;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class MapUtils {

    private MapUtils() {
    }

    /**
     * printMap: 打印给定映射集合
     *
     * @param map 给定映射集合
     */

    public static <K, V> void printMap(Map<K, V> map) {
        Set<Map.Entry<K, V>> resultsEntry = map.entrySet();
        Iterator<Map.Entry<K, V>> iter = resultsEntry.iterator();
        while (iter.hasNext()) {
            Map.Entry<K, V> result = iter.next();
            System.out.println(result.getKey() + " = " + result.getValue());
        }
    }

    /**
     * sortMap: 根据映射值的大小，对映射集合进行排序。
     */
    public static <K, V> List<Element<K, V>> sortMap(Map<K, V> map) {
        List<Element<K, V>> elements = new ArrayList<Element<K, V>>();
        Set<Map.Entry<K, V>> mapEntries = map.entrySet();
        Iterator<Map.Entry<K, V>> iter = mapEntries.iterator();
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            elements.add(new Element<K, V>(entry.getKey(), entry.getValue()));
        }
        Collections.sort(elements);
        return elements;
    }

    /**
     * printList: 打印列表内容
     */
    public static <T> void print(List<T> list) {
        System.out.println("List: [");
        for (T t : list) {
            System.out.println(t);
        }
        System.out.println("]");
    }

    public static Object readVal(Map<String, Object> map, String path) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        return readVal(map, path.split("\\."));
    }

    private static Object readVal(Map<String, Object> map, String[] subpaths) {
        Object val = map;
        try {
            for (String subpath : subpaths) {
                if (val != null && val instanceof Map) {
                    val = ((Map) val).get(subpath);
                } else {
                    return null;
                }
            }
            return val == null ? null : val;
        } catch (Exception ex) {
            return null;
        }

    }

    public static void addValue(Map<String, Set<String>> map, String key, String value) {
        map.computeIfAbsent(key, k -> Sets.newHashSet()).add(value);
    }

    public static <T> void addValue(Map<String, List<T>> map, String key, T value) {
        map.computeIfAbsent(key, k -> Lists.newArrayList()).add(value);
    }

    public static Set<String> getKeysByValue(Map<String, String> map, String value) {
        Set<String> keys = new HashSet<>();
        map.forEach((k, v) -> {
            if (v.equals(value)) {
                keys.add(k);
            }
        });
        return keys;
    }

    public static <K, V> Map<K, V> buildMap(K key, V value) {
        Map<K, V> map = new HashMap<>(JavaCodeGuideConstants.MAP_SIZE_2);
        map.put(key, value);
        return map;
    }

    public static <K, V> void addValueGeneric(Map<K, Set<V>> map, K key, V value) {
        map.computeIfAbsent(key, k -> Sets.newHashSet()).add(value);
    }

    public static <T, K, V> Map<K, Set<V>> convertToMap(List<T> list, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        Map<K, Set<V>> map = new HashMap<>(JavaCodeGuideConstants.MAP_SIZE_32);
        for (T item: list) {
            addValueGeneric(map, keyFunc.apply(item), valueFunc.apply(item));
        }
        return map;
    }

    public static <T, K, V> Map<K, Set<V>> convertToMap(List<T> list, Predicate<T> filterFunc, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        Map<K, Set<V>> map = new HashMap<>(JavaCodeGuideConstants.MAP_SIZE_32);
        for (T item: list) {
            if (filterFunc.test(item)) {
                addValueGeneric(map, keyFunc.apply(item), valueFunc.apply(item));
            }
        }
        return map;
    }

    private static class Element<K, V> implements Comparable<Element<K, V>> {

        private K key;
        private V value;

        public Element(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(Element<K, V> e) {

            try {
                Method m = value.getClass().getMethod("compareTo", value.getClass());
                return -((Integer) m.invoke(value, e.value));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return 0;  // dangerous! 当出现异常时，？？？
        }

        public String toString() {
            return "(" + key + " , " + value + ")";
        }


    }

}

