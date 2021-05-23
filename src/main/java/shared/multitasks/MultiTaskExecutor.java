package shared.multitasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

import javax.annotation.Resource;

import shared.multitasks.customized.MyThreadPoolExecutor;
import shared.rpc.batchcall.WrapperListHandlerParam;
import zzz.study.algorithm.dividing.Dividing;
import zzz.study.function.refactor.ForeachUtil;

/**
 * Created by shuqin on 18/3/13.
 */
public class MultiTaskExecutor {

  private static Logger logger = LoggerFactory.getLogger(MultiTaskExecutor.class);

  private static final int TASK_SIZE = 50;
  
  private ThreadPoolExecutor taskExecutor = MyThreadPoolExecutor.getInstance("multitasks").getDelegated();

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
  public <T,R> List<R> exec(List<T> allKeys, Function<List<T>, List<R>> handleBizDataFunc) {
    return exec(allKeys, handleBizDataFunc, TASK_SIZE);
  }

  public <T,R> List<R> exec(List<T> allKeys, Function<List<T>, List<R>> handleBizDataFunc, int taskSize) {
    Function<WrapperListHandlerParam<T,R>, List<R>> wrapperhandleBizDataFunc =
        (wrapperListParam) -> handleBizDataFunc.apply(wrapperListParam.getKeys());
    WrapperListHandlerParam
        wrapperListParam = new WrapperListHandlerParam(allKeys, wrapperhandleBizDataFunc);
    return exec(wrapperListParam, wrapperhandleBizDataFunc, taskSize);
  }

  public <T,R> List<R> exec(WrapperListHandlerParam<T,R> listParam, Function<WrapperListHandlerParam<T,R>, List<R>> handleBizDataFunc, int taskSize) {
    List<T> allKeys = listParam.getKeys();
    List<String> parts = Dividing.divide(allKeys.size(), taskSize);

    CompletionService<List<R>>
        completionService = new ExecutorCompletionService(taskExecutor);

    ForeachUtil.foreachDone(parts, (part) -> {
      final List<T> tmpRowkeyList = Dividing.getSubList(allKeys, part);
      WrapperListHandlerParam
          subListParam = new WrapperListHandlerParam(tmpRowkeyList, handleBizDataFunc);
      completionService.submit(
          () -> handleBizDataFunc.apply(subListParam));
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        logger.warn("failed to sleep when concurrently execute handleBizDataFunc", e);
      }
    });

    List<R> result = ForeachUtil.foreachAddWithReturn(parts.size(), (ind) -> get(ind, completionService));
    return result;
  }

  public <T> List<T> get(int ind, CompletionService<List<T>> completionService) {
    try {
      return completionService.take().get();
    } catch (Exception e) {
      logger.error("failed to get data from completionService " + e.getMessage(), e);
      throw new RuntimeException(e.getCause());
    }

  }

}

