package shared.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * PageUtils:
 * created by qin.shu 2023/3/10
 */
public class PageUtils {

    public static <T> List<T> getPagedList(List<T> list, Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 50;
        }
        int start = page * size;
        int end = (page + 1) * size;
        if (list.size() < start) {
            return Lists.newArrayList();
        }
        if (list.size() < end) {
            end = list.size();
        }
        return list.subList(start, end);
    }
}
