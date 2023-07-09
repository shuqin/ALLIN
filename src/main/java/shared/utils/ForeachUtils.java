package shared.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by shuqin on 18/3/13.
 */
public class ForeachUtils {

    public static <T> List<T> foreachAddWithReturn(int num, Function<Integer, List<T>> getFunc) {
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < num; i++) {
            List<T> partResult = ExceptionUtils.doWithRobust(getFunc, i);
            if (CollectionUtils.isNotEmpty(partResult)) {
                result.addAll(partResult);
            }
        }
        return result;
    }

    public static <T> void foreachDone(List<T> data, Consumer<T> doFunc) {
        for (T part : data) {
            ExceptionUtils.doWithRobust(doFunc, part);
        }
    }

}
