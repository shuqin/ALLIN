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

import org.apache.log4j.Logger;

public class MyThreadPoolExecutor {

  private static final Logger log = Logger.getLogger("threadMonitor");

  private static ConcurrentMap<String, MyThreadPoolExecutor> poolCache = new ConcurrentHashMap<String, MyThreadPoolExecutor>();

  private static MyThreadPoolExecutor myThreadPool = null;

  private int corePoolSize = 3;   // 线程池维护线程的最小数量

  private int maximumPoolSize = 5;  // 线程池维护线程的最大数量

  private long keepAliveTime = 60;  // 线程池维护线程所允许的空闲时间

  private final TimeUnit unit = TimeUnit.SECONDS;  // 线程池维护线程所允许的空闲时间的单位

  private BlockingQueue<Runnable> workQueue = null; // 线程池所使用的缓冲队列

  private final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();  // 线程池对拒绝任务的处理策略

  private ThreadPoolExecutor threadPool = null;

  private MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                               int timeoutQueueSize,
                               String namePrefix) {
    this.corePoolSize = corePoolSize > 0 ? corePoolSize : this.corePoolSize;
    this.maximumPoolSize = maximumPoolSize > 0 ? maximumPoolSize : this.maximumPoolSize;
    this.keepAliveTime = keepAliveTime;
    workQueue = new ArrayBlockingQueue<Runnable>(timeoutQueueSize);
    threadPool =
        new MonitoredThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                                        workQueue,
                                        new MoonmmThreadFactory(namePrefix), handler);
  }

  /**
   * 实例化线程池
   */
  public static MyThreadPoolExecutor getInstance(int corePoolSize, int maximumPoolSize,
                                                 long keepAliveTime,
                                                 int timeoutQueueSize, String namePrefix) {
    if (poolCache.get(namePrefix) == null) {
      myThreadPool = new MyThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                                              timeoutQueueSize, namePrefix);
      poolCache.put(namePrefix, myThreadPool);
    }
    return myThreadPool;
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
  static class MoonmmThreadFactory implements ThreadFactory {

    final AtomicInteger threadNumber = new AtomicInteger(1);
    String namePrefix = "";

    public MoonmmThreadFactory(String namePrefix) {
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
            log.info(String.format("Task %s: time=%dms", taskInfo, taskTime));
          }
        }
      } finally {
        super.afterExecute(r, t);
      }
    }

    public void logThreadPoolMonitorData() {
      log.info("total tasks completed: " + numTasks.get());
      log.info("totalTime: " + totalTime.get());
    }

    public Map<String, Object> obtainThreadPoolMonitorData() {
      Map<String, Object> monitorData = new HashMap<String, Object>();
      monitorData.put("total_task", numTasks.get());
      monitorData.put("total_time", totalTime.get());
      return monitorData;
    }

  }

  public static <T> List<T> getMultiTaskResult(List<FutureTask<List<T>>> futureTaskList) {
    List<T> results = new ArrayList<T>();
    for (FutureTask<List<T>> futureTask : futureTaskList) {
      try {
        // 每个线程设置固定的执行时间，过期不候
        List<T> partResultList = futureTask.get(ThreadConstants.TASK_WAIT_TIME, TimeUnit.SECONDS);
        if (partResultList != null && partResultList.size() > 0) {
          for (T file : partResultList) {
            results.add(file);
          }
        }
      } catch (TimeoutException e) {
        log.error(futureTask.getClass() + " Multi thread timeout error: " + Thread.currentThread()
                      .getName(),
                  e);
      } catch (InterruptedException e) {
        log.error(futureTask.getClass() + " Multi thread interrupted error: "
                  + Thread.currentThread().getName(), e);
      } catch (ExecutionException e) {
        log.error(futureTask.getClass() + " Multi thread execution error: "
                  + Thread.currentThread().getName(), e);
      }
    }
    return results;
  }

}
