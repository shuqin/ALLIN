package shared.utils;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * 排序相关的工具类
 */
public class SortUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SortUtils.class);

    /**
     * Sort 对象转为 Comparator 对象
     */
    public static <T> Comparator<T> sortToComparator(Sort sort, Class<T> cls) {
        if (sort == null) {
            return null;
        }

        Comparator<T> comparator = null;

        for (Sort.Order order : sort) {
            boolean isAscending = order.getDirection().isAscending();
            String field = order.getProperty();
            Comparator<T> nextComparator = getComparator(convert(cls, field), isAscending);
            if (nextComparator != null) {
                if (comparator == null) {
                    comparator = nextComparator;
                } else {
                    comparator = comparator.thenComparing(nextComparator);
                }
            }
        }

        return comparator;
    }

    /**
     * 将sort对象中的字段名改为驼峰
     */
    public static Sort convertSortFieldToCamel(Sort sort) {
        if (sort == null) {
            return null;
        }

        List<Sort.Order> orderList = new ArrayList<>();
        for (Sort.Order order : sort) {
            orderList.add(new Sort.Order(order.getDirection(), StringUtils.underlineToCamel(order.getProperty())));
        }

        return Sort.by(orderList);
    }

    private static <T> Function<T, ? extends Comparable> convert(Class<T> cls, String fieldname) {
        return o -> {
            try {
                Field field = FieldUtils.getDeclaredField(cls, fieldname, true);
                if (field != null) {
                    return (Comparable) field.get(o);
                }
            } catch (IllegalAccessException e) {
                LOG.error(e.getMessage(), e);
            }
            return null;
        };
    }

    private static <T> Comparator<T> getComparator(Function<T, ? extends Comparable> func, boolean isAscending) {
        if (func == null) {
            return null;
        }
        return isAscending ? Comparator.comparing(func) : (o1, o2) -> func.apply(o2).compareTo(func.apply(o1));
    }

}
