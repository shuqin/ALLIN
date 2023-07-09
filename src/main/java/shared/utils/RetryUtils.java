package shared.utils;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


/**
 * Created by lirui01 on 2023/4/14
 * 重试工具类
 */
public final class RetryUtils {

    private static final Logger logger = LoggerFactory.getLogger(RetryUtils.class);

    //重试策略默认值
    private static final int MAX_RETRY_TIMES = 3;
    private static final int RETRY_INTERVAL = 1;

    public static final String RETRY_SUCCESS = "success";


    private RetryUtils() {
    }

    /**
     * 默认重试机制
     * @param callable 重试操作
     * @param <T> 返回值
     * @return
     */
    public static <T> T call(Callable<T> callable) {
        try {
            return call(callable, MAX_RETRY_TIMES, RETRY_INTERVAL);
        } catch (Exception e) {
            logger.error("retry error", e);
        }
        return null;
    }

    /**
     * 带失败处理的重试机制
     * @param callable 重试操作
     * @param recovery 重试达到限制后任然失败后的处理
     * @param <T> 返回值
     * @return
     */
    public static <T> T call(Callable<T> callable, Runnable recovery) {
        try {
            return call(callable, MAX_RETRY_TIMES, RETRY_INTERVAL);
        } catch (Exception e) {
            logger.error("retry error", e);
            recovery.run();
        }
        return null;
    }

    /**
     * 自定义重试机制
     * @param callable 重试操作
     * @param maxRetryTimes 最大重试次数
     * @param retryInterval 重试间隔
     * @param <T> 返回值
     * @return
     */
    public static <T> T call(Callable<T> callable, int maxRetryTimes, int retryInterval) {
        try {
            Retryer<T> retry = buildExceptionRetryer(maxRetryTimes, retryInterval);
            return retry.call(callable);
        } catch (Exception e) {
            logger.error("retry error", e);
        }
        return null;
    }

    /**
     * 无返回值的重试机制
     * @param runnable 重试操作
     */
    public static void call(Runnable runnable) {
        try {
            call(() -> {
                runnable.run();
                return RETRY_SUCCESS;
            });
        } catch (Exception e) {
            logger.error("retry error", e);
        }
    }

    /**
     * 根据返回结果条件判断的重试机制
     * @param callable 重试操作
     * @param predicate 重试条件
     * @param <T> 返回值
     */
    public static <T> T call(Callable<T> callable, Predicate<T> predicate) {
        try {
            Retryer<T> retry = buildResultRetryer(predicate, MAX_RETRY_TIMES, RETRY_INTERVAL);
            return retry.call(callable);
        } catch (Exception e) {
            logger.error("retry error", e);
        }
        return null;
    }


    private static <T> Retryer<T> buildExceptionRetryer(int maxRetryTimes, int retryInterval) {
        return RetryerBuilder.<T>newBuilder()
                .retryIfException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(maxRetryTimes))
                .withWaitStrategy(WaitStrategies.fixedWait(retryInterval, TimeUnit.SECONDS))
                .build();
    }

    private static <T> Retryer<T> buildResultRetryer(Predicate<T> predicate, int maxRetryTimes, int retryInterval) {
        return RetryerBuilder.<T>newBuilder()
                .retryIfResult(predicate)
                .retryIfException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(maxRetryTimes))
                .withWaitStrategy(WaitStrategies.fixedWait(retryInterval, TimeUnit.SECONDS))
                .build();
    }

}
