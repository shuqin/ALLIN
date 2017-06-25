package zzz.study.function.refactor.result;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import zzz.study.function.refactor.CatchUtil;
import zzz.study.function.refactor.ExecutorUtil;
import zzz.study.function.refactor.ForeachUtil;
import zzz.study.function.refactor.StreamUtil;

/**
 * Created by shuqin on 17/6/23.
 */
public class ConcurrentDataHandlerFrameRefactored {

  public static void main(String[] args) {
    List<Integer> allData = getAllData(DataSupplier::getKeys, GetTradeData::getData);
    consumer(allData, System.out::println);

    List<Double> handledData = handleAllData(allData,
         (numbers) -> StreamUtil.map(numbers, (num) -> Math.sqrt(num)) );
    consumer(handledData, System.out::println);

  }

  /**
   * 获取所有业务数据
   *
   * 回调的替换
   */
  public static <T> List<T> getAllData(Supplier<List<String>> getAllKeysFunc, Function<List<String>, List<T>> iGetBizDataFunc) {
    return getAllData(getAllKeysFunc.get(), iGetBizDataFunc);
  }

  public static <T> List<T> getAllData(List<String> allKeys, Function<List<String>, List<T>> iGetBizDataFunc) {
    return handleAllData(allKeys, iGetBizDataFunc);
  }

  public static <T,R> List<R> handleAllData(Supplier<List<T>> getAllKeysFunc, Function<List<T>, List<R>> handleBizDataFunc) {
    return handleAllData(getAllKeysFunc.get(), handleBizDataFunc);
  }

  public static <T,R> List<R> handleAllData(List<T> allKeys, Function<List<T>, List<R>> handleBizDataFunc) {
    return ExecutorUtil.exec(allKeys, handleBizDataFunc);
  }

  public static <T> void consumer(List<T> data, Consumer<T> consumer) {
    data.forEach( (t) -> CatchUtil.tryDo(t, consumer) );
  }

  public static class DataSupplier {
    public static List<String> getKeys() {
      // foreach code refining
      return ForeachUtil.foreachAddWithReturn(2000, (ind -> Arrays.asList(String.valueOf(ind))));
    }
  }

  /** 获取业务数据具体实现 */
  public static class GetTradeData {

    public static List<Integer> getData(List<String> keys) {
      // maybe xxxService.getData(keys);
      return StreamUtil.map(keys, key -> Integer.valueOf(key) % 1000000000);  // stream replace foreach
    }

  }

}



