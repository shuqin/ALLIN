package zzz.study.function.comparator;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.function.Function;

/**
 * 差异点：
 * 1. 根据指定字段比较；
 * 2. 根据指定排序；
 * 3. 返回一个指定对象的比较器
 */
public class ComparatorGenerator {

    public static Comparator<? extends ComparatorObject> getComparator(Function<ComparatorObject, Object> compFunc, boolean isAscending) {
        return isAscending ? (o1, o2) -> (StringUtils.compare(String.valueOf(compFunc.apply(o1)), String.valueOf(compFunc.apply(o2)))) :
                (o1, o2) -> (StringUtils.compare(String.valueOf(compFunc.apply(o2)), String.valueOf(compFunc.apply(o1))));
    }

    public static Comparator<? extends ComparatorObject> getComparator(Sort sort) {
        return getComparator(sort, "firstTime");
    }

    public static Comparator<? extends ComparatorObject> getComparator(Sort sort, String defaultField) {
        if (sort == null) {
            return getComparator(ComparatorFuncEnum.getCompFunc(defaultField), true);
        }

        if (sort.iterator().hasNext()) {
            Sort.Order order = sort.iterator().next();
            Function<ComparatorObject, Object> compFunc = ComparatorFuncEnum.getCompFunc(order.getProperty());
            return getComparator(compFunc, order.getDirection().isAscending());
        }

        return getComparator(ComparatorFuncEnum.getCompFunc(defaultField), true);
    }


    @Getter
    enum ComparatorFuncEnum {
        firstTime("firstTime", ComparatorObject::getFirstTime),
        lastTime("lastTime", ComparatorObject::getLastTime),
        visitCount("visitCount", ComparatorObject::getVisitCount),
        ;

        Function<ComparatorObject, Object> compFunc;
        private String field;

        ComparatorFuncEnum(String field, Function<ComparatorObject, Object> compFunc) {
            this.field = field;
            this.compFunc = compFunc;
        }

        public static Function<ComparatorObject, Object> getCompFunc(String field) {
            for (ComparatorFuncEnum cfe : ComparatorFuncEnum.values()) {
                if (cfe.getField().equals(field)) {
                    return cfe.compFunc;
                }
            }
            return null;
        }
    }
}
