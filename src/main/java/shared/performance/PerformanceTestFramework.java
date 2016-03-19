package shared.performance;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 性能测试简易框架
 * Created by qinshu on 2021/7/11
 */
public class PerformanceTestFramework {

    public static final Integer DATA_SIZE = 100;
    public static final Integer RUNTIMES = 1000;

    public static final Integer THREAD_NUMS = 5;

    static List<Long> costs = new CopyOnWriteArrayList<>();
    static int errorCount = 0;

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1024));

    public static TestStrategyEnum SEQUENTIAL = TestStrategyEnum.SEQUENTIAL;
    public static TestStrategyEnum CONCURRENT = TestStrategyEnum.CONCURRENT;

    private static TestStrategy sequentialStrategy = new SeqTestStrategy();
    private static TestStrategy concurrentStrategy = new ConcurrentTestStrategy();

    public static void test(ConsumerWrapper consumerWrapper, TestStrategyEnum strategyEnum) {
        choose(strategyEnum).test(consumerWrapper);
    }

    private static TestStrategy choose(TestStrategyEnum strategyEnum) {
        return strategyEnum == TestStrategyEnum.SEQUENTIAL ? sequentialStrategy : concurrentStrategy;
    }

    enum TestStrategyEnum {
        SEQUENTIAL,
        CONCURRENT;
    }


    static class ConcurrentTestStrategy implements TestStrategy {

        @Override
        public void test(ConsumerWrapper consumerWrapper) {

            reset();

            CountDownLatch cdl = new CountDownLatch(RUNTIMES);
            for (int i=0; i < RUNTIMES; i++) {
                executorService.submit(() -> {
                    time(consumerWrapper);
                    cdl.countDown();
                });
            }
            try {
                cdl.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executorService.shutdown();
            executorService.shutdownNow();

            report(this.getClass().getName());
        }
    }

    static class SeqTestStrategy implements TestStrategy {

        @Override
        public void test(ConsumerWrapper consumerWrapper) {

            reset();

            for (int i=0; i < RUNTIMES; i++) {
                time(consumerWrapper);
            }

            report(this.getClass().getName());
        }
    }

    interface TestStrategy {
        void test(ConsumerWrapper consumerWrapper);
    }

    private static void time(ConsumerWrapper consumerWrapper) {
        long start = System.currentTimeMillis();
        consumerWrapper.run();
        long end = System.currentTimeMillis();
        costs.add(end-start);
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((end-start) + "ms");
    }

    private static void report(String className) {
        costs.set(0, costs.get(costs.size()-1));
        costs.remove(costs.size()-1);
        IntSummaryStatistics costStats = costs.stream().collect(Collectors.summarizingInt(Long::intValue));
        System.out.println(className + " Test: cost avg: " + costStats.getAverage() + " min: " + costStats.getMin() + " max: " + costStats.getMax() + " count: " + costStats.getCount());

        System.out.println(className + " Test: errorCount: " + errorCount);
    }

    private static void reset() {
        costs.clear();
        errorCount = 0;
    }
}
