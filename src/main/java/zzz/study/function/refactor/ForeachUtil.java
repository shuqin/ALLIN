package zzz.study.function.refactor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import zzz.study.function.refactor.result.CatchUtil;

/**
 * Created by shuqin on 17/6/24.
 *
 * foreach 代码通用模板
 */
public class ForeachUtil {

  public static <T> List<T> foreachAddWithReturn(int num, Function<Integer, List<T>> getFunc) {
    List<T> result = new ArrayList<T>();
    for (int i=0; i< num; i++) {
      result.addAll(CatchUtil.tryDo(i, getFunc));

    }
    return result;
  }

  public static <T> void foreachDone(List<T> data, Consumer<T> doFunc) {
    for (T part: data) {
      CatchUtil.tryDo(part, doFunc);
    }
  }

}
