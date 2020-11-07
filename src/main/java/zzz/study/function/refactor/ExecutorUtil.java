package zzz.study.function.refactor;

import zzz.study.algorithm.dividing.Dividing;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by shuqin on 17/6/25.
 */
public class ExecutorUtil {

  private ExecutorUtil() {}

  private static final int CORE_CPUS = Runtime.getRuntime().availableProcessors();
  private static final int TASK_SIZE = 1000;

  // a throol pool may be managed by spring
  private static ExecutorService executor = new ThreadPoolExecutor(
      CORE_CPUS, 10, 60L, TimeUnit.SECONDS,
      new ArrayBlockingQueue<>(60));

  /**
   * 根据指定的列表关键数据及列表数据处理器，并发地处理并返回处理后的列表数据集合
   * @param allKeys 列表关键数据
   * @param handleBizDataFunc 列表数据处理器
   * @param <T> 待处理的数据参数类型
   * @param <R> 待返回的数据结果类型
   * @return 处理后的列表数据集合
   *
   * NOTE: 类似实现了 stream.par.map 的功能，不带延迟计算
   */
  public static <T,R> List<R> exec(List<T> allKeys, Function<List<T>, List<R>> handleBizDataFunc) {
    List<String> parts = Dividing.divide(allKeys.size(), TASK_SIZE);
    //System.out.println(parts);

    CompletionService<List<R>>
        completionService = new ExecutorCompletionService<>(executor);

    ForeachUtil.foreachDone(parts, (part) -> {
      final List<T> tmpRowkeyList = Dividing.getSubList(allKeys, part);
      completionService.submit(
          () -> handleBizDataFunc.apply(tmpRowkeyList));  // lambda replace inner class
    });

    // foreach code refining
    List<R> result = ForeachUtil.foreachAddWithReturn(parts.size(), (ind) -> get(ind, completionService));
    return result;
  }

  /**
   * 根据指定的列表关键数据及列表数据处理器，并发地处理
   * @param allKeys 列表关键数据
   * @param handleBizDataFunc 列表数据处理器
   * @param <T> 待处理的数据参数类型
   *
   * NOTE: foreachDone 的并发版
   */
  public static <T> void exec(List<T> allKeys, Consumer<List<T>> handleBizDataFunc) {
    List<String> parts = Dividing.divide(allKeys.size(), TASK_SIZE);
    //System.out.println(parts);

    ForeachUtil.foreachDone(parts, (part) -> {
      final List<T> tmpRowkeyList = Dividing.getSubList(allKeys, part);
      executor.execute(
          () -> handleBizDataFunc.accept(tmpRowkeyList));  // lambda replace inner class
    });
  }

  public static <T> List<T> get(int ind, CompletionService<List<T>> completionService) {
    // lambda cannot handler checked exception
    try {
      return completionService.take().get();
    } catch (Exception e) {
      e.printStackTrace();  // for log
      throw new RuntimeException(e.getCause());
    }

  }

}
