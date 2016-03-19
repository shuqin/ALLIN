package zzz.study.function.comparator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

/**
 * 差异点：
 * 1. 根据指定字段比较；
 * 2. 根据指定排序；
 * 3. 返回一个指定对象的比较器
 */
public class ComparatorGenerator2 {

    public static <T> Comparator<T> getComparator(Function<T, String> compFunc, boolean isAscending) {
        return isAscending ? (o1, o2) -> (StringUtils.compare(String.valueOf(compFunc.apply(o1)), String.valueOf(compFunc.apply(o2)))) :
                (o1, o2) -> (StringUtils.compare(String.valueOf(compFunc.apply(o2)), String.valueOf(compFunc.apply(o1))));
    }

    public static <T> Comparator<T> getComparator(Sort sort, Class<T> cls) {
        if (sort == null) {
            return getComparator(convert(cls, getDefaultField(cls)), true);
        }
        if (sort.iterator().hasNext()) {
            Sort.Order order = sort.iterator().next();
            boolean isAscending = order.getDirection().isAscending();
            String field = order.getProperty();
            return getComparator(convert(cls, field), isAscending);
        }
        return getComparator(convert(cls, getDefaultField(cls)), true);
    }

    public static <T> Function<T, String> convert(Class<T> cls, String field) {
        return o -> {
            try {
                return String.valueOf(FieldUtils.getDeclaredField(cls, field, true).get(o));
            } catch (IllegalAccessException e) {
                return "";
            }
        };
    }

    private static String getDefaultField(Class cls) {
        return Arrays.stream(cls.getFields())
                .filter(f -> "DEFAULT_SORT_TIME".equals(f.getName()))
                .findFirst().toString();
    }
}
