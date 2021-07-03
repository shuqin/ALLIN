package zzz.study.patterns.singleton;

public class SynchronizedSingletonWithLowPerformance {

    private static SynchronizedSingletonWithLowPerformance singleton;
    private static Object classLock = SynchronizedSingletonWithLowPerformance.class;

    private SynchronizedSingletonWithLowPerformance() {
    }

    public static SynchronizedSingletonWithLowPerformance getInstance() {
        synchronized (classLock) {  // 每次调用 getInstance 都要加锁
            if (singleton == null) {
                singleton = new SynchronizedSingletonWithLowPerformance();
            }
            return singleton;
        }
    }
}
