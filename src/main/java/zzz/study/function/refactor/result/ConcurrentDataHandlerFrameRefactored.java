package zzz.study.function.refactor.result;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import zzz.study.function.refactor.ForeachUtil;
import zzz.study.function.refactor.StreamUtil;
import zzz.study.function.refactor.TaskUtil;

/**
 * Created by shuqin on 17/6/23.
 */
public class ConcurrentDataHandlerFrameRefactored {

  public static void main(String[] args) {
    List<Integer> allData = getAllData(getKeys(), GetTradeData::getData);
    System.out.println(allData);
  }

  public static List<String> getKeys() {
    // foreach code refining
    return ForeachUtil.foreachAddWithReturn(2000, (ind -> Arrays.asList(String.valueOf(ind))));
  }

  /**
   * 获取所有业务数据
   *
   * 回调的替换
   */
  public static <T> List<T> getAllData(List<String> allKeys, Function<List<String>, List<T>> iGetBizDataFunc) {
    List<String> parts = TaskUtil.divide(allKeys.size(), 1000);
    System.out.println(parts);
    ExecutorService executor = Executors.newFixedThreadPool(parts.size());
    CompletionService<List<T>>
        completionService = new ExecutorCompletionService<List<T>>(executor);

    ForeachUtil.foreachDone(parts, (part) -> {
      final List<String> tmpRowkeyList = TaskUtil.getSubList(allKeys, part);
      completionService.submit(() -> iGetBizDataFunc.apply(tmpRowkeyList));  // lambda replace inner class
    });

    // 这里是先完成先加入, 不保证报表行顺序, 因此在获取所有的报表行后要进行排序便于商家查看
    // foreach code refining
    List<T> result = ForeachUtil.foreachAddWithReturn(parts.size(), (ind) -> get(ind, completionService));
    executor.shutdown();
    return result;
  }

  public static <T> List<T> get(int ind, CompletionService<List<T>> completionService) {
    try {
      return completionService.take().get();
    } catch (Exception e) {
      e.printStackTrace();  // for log
      throw new RuntimeException(e.getCause());
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



