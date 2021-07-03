package zzz.study.patterns.singleton;

public class SynchronizedSingletonWithBug {

    private static SynchronizedSingletonWithBug singleton;
    private static Object classLock = SynchronizedSingletonWithBug.class;

    private SynchronizedSingletonWithBug() {
    }

    public static SynchronizedSingletonWithBug getInstance() {
        if (singleton == null) {
            synchronized (classLock) {
                if (singleton == null) {
                    singleton = new SynchronizedSingletonWithBug();
                }
            }
        }
        return singleton;
    }
}
