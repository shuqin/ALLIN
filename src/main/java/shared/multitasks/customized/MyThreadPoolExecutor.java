package shared.multitasks.customized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MyThreadPoolExecutor:
 * 提供了针对真正执行任务的线程池 threadPool 的代理访问，同时提供了线程池参数配置能力
 */
public class MyThreadPoolExecutor {

  private static final Logger logger = LoggerFactory.getLogger(MyThreadPoolExecutor.class);

  private static ConcurrentMap<String, MyThreadPoolExecutor> poolCache = new ConcurrentHashMap<String, MyThreadPoolExecutor>();

  private static final int DEFAULT_CORE_POOL_SIZE = 5;
  private static final int DEFAULT_MAX_POOL_SIZE = 10;
  private static final long DEFAULT_KEEP_ALIVE_TIME_SECS = 60;
  private static final int DEFAULT_TIMEOUT_QUEUE_SIZE = 60;
  private static final long TASK_WAIT_TIME = 30L;

  private static final int MAX_THREAD_POOL_NUM = 10;

  private int corePoolSize = DEFAULT_CORE_POOL_SIZE;   // 线程池维护线程的最小数量

  private int maximumPoolSize = DEFAULT_MAX_POOL_SIZE;  // 线程池维护线程的最大数量

  private long keepAliveTime = DEFAULT_KEEP_ALIVE_TIME_SECS;  // 线程池维护线程所允许的空闲时间

  private final TimeUnit unit = TimeUnit.SECONDS;  // 线程池维护线程所允许的空闲时间的单位

  private BlockingQueue<Runnable> workQueue = null; // 线程池所使用的缓冲队列

  private final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();  // 线程池对拒绝任务的处理策略

  private ThreadPoolExecutor threadPool = null;

  private static MyThreadPoolExecutor DEFAULT = new MyThreadPoolExecutor(
          DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE, DEFAULT_KEEP_ALIVE_TIME_SECS,
          DEFAULT_TIMEOUT_QUEUE_SIZE, "default");

  private MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                               int timeoutQueueSize,
                               String namePrefix) {
    this.corePoolSize = corePoolSize > 0 ? corePoolSize : this.corePoolSize;
    this.maximumPoolSize = maximumPoolSize > 0 ? maximumPoolSize : this.maximumPoolSize;
    this.keepAliveTime = keepAliveTime;
    workQueue = new ArrayBlockingQueue<Runnable>(timeoutQueueSize);
    threadPool =
        new MonitoredThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                                        workQueue, new NamedThreadFactory(namePrefix), handler);
  }

  /**
   * 实例化线程池
   */
  public static MyThreadPoolExecutor getInstance(String namePrefix) {
    MyThreadPoolExecutor myThreadPool = poolCache.get(namePrefix);
    if (myThreadPool != null) {
      return myThreadPool;
    }
    synchronized (poolCache) {
      if (poolCache.size() < MAX_THREAD_POOL_NUM) {
        myThreadPool = new MyThreadPoolExecutor(
                DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE, DEFAULT_KEEP_ALIVE_TIME_SECS,
                DEFAULT_TIMEOUT_QUEUE_SIZE, namePrefix);
        poolCache.put(namePrefix, myThreadPool);
        return myThreadPool;
      }
    }
    return DEFAULT;
  }

  public static MyThreadPoolExecutor getInstance(int corePoolSize, int maximumPoolSize,
                                                 long keepAliveTime,
                                                 int timeoutQueueSize, String namePrefix) {
    MyThreadPoolExecutor myThreadPool = poolCache.get(namePrefix);
    if (myThreadPool == null) {
      myThreadPool = new MyThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                                              timeoutQueueSize, namePrefix);
      poolCache.put(namePrefix, myThreadPool);
    }
    return myThreadPool;
  }

  public ThreadPoolExecutor getDelegated() {
    return threadPool;
  }

  /**
   * 通过线程池执行Runable
   */
  public void execute(FutureTask task) {
    threadPool.execute(task);
  }

  public void execute(Runnable r) {
    threadPool.execute(r);
  }

  public <T,R> List<R> getMultiTaskResult(List<T> origin, Function<T,R> func) {
    List<FutureTask<R>> futureTasks = new ArrayList<>();
    for (T t: origin) {
      futureTasks.add(new FutureTask(() -> func.apply(t)));
    }
    return getMultiTaskResult(futureTasks);
  }

  /**
   * 关闭所有线程
   */
  public void shutdown() {
    threadPool.shutdown();
  }

  /**
   * 返回核心线程数
   */
  public int getCorePoolSize() {
    return corePoolSize;
  }

  /**
   * 返回最大线程数
   */
  public int getMaximumPoolSize() {
    return maximumPoolSize;
  }

  /**
   * 返回线程的最大空闲时间
   */
  public long getKeepAliveTime() {
    return keepAliveTime;
  }

  /**
   * 线程工厂类
   */
  static class NamedThreadFactory implements ThreadFactory {

    final AtomicInteger threadNumber = new AtomicInteger(1);
    String namePrefix = "";

    public NamedThreadFactory(String namePrefix) {
      this.namePrefix = namePrefix;
    }

    public Thread newThread(Runnable r) {
      Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
      t.setDaemon(false);
      if (t.getPriority() != Thread.NORM_PRIORITY) {
        t.setPriority(Thread.NORM_PRIORITY);
      }
      return t;
    }
  }

  /**
   * 基于 ThreadPoolExecutor 扩展了任务描述和计时能力
   */
  static class MonitoredThreadPoolExecutor extends ThreadPoolExecutor {

    public MonitoredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                       TimeUnit unit,
                                       BlockingQueue<Runnable> workQueue,
                                       ThreadFactory threadFactory,
                                       RejectedExecutionHandler handler) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    protected void beforeExecute(Thread t, Runnable r) {
      super.beforeExecute(t, r);
      startTime.set(System.currentTimeMillis());
    }

    protected void afterExecute(Runnable r, Throwable t) {
      try {
        long endTime = System.currentTimeMillis();
        long taskTime = endTime - startTime.get();
        numTasks.incrementAndGet();
        totalTime.addAndGet(taskTime);
        if (r instanceof FutureTaskWithCallableAvailed) {
          Callable c = ((FutureTaskWithCallableAvailed) r).getTask();
          if (c instanceof TaskInfo) {
            String taskInfo = ((TaskInfo) c).desc();
            logger.info("Task {}: time={}ms", taskInfo, taskTime);
          }
        }
      } finally {
        super.afterExecute(r, t);
      }
    }

    public Map<String, Object> obtainThreadPoolMonitorData() {
      Map<String, Object> monitorData = new HashMap<String, Object>();
      monitorData.put("total_task", numTasks.get());
      monitorData.put("total_time", totalTime.get());
      return monitorData;
    }

  }

  private <T> List<T> getMultiTaskResult(List<FutureTask<T>> futureTaskList) {
    List<T> results = new ArrayList<T>();
    for (FutureTask<T> futureTask : futureTaskList) {
      try {
        // 每个线程设置固定的执行时间，过期不候
        T partResult = futureTask.get(TASK_WAIT_TIME, TimeUnit.SECONDS);
        results.add(partResult);
      } catch (TimeoutException e) {
        logger.error(futureTask.getClass() + " Multi thread timeout error: " + Thread.currentThread()
                      .getName(),
                  e);
      } catch (InterruptedException e) {
        logger.error(futureTask.getClass() + " Multi thread interrupted error: "
                  + Thread.currentThread().getName(), e);
      } catch (ExecutionException e) {
        logger.error(futureTask.getClass() + " Multi thread execution error: "
                  + Thread.currentThread().getName(), e);
      }
    }
    return results;
  }

}
