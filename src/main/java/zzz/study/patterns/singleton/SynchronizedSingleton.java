package zzz.study.patterns.singleton;

public class SynchronizedSingleton {

    private static volatile SynchronizedSingleton singleton;
    private static Object classLock = SynchronizedSingleton.class;

    private SynchronizedSingleton() {
    }

    public static SynchronizedSingleton getInstance() {
        if (singleton == null) {
            synchronized (classLock) {
                if (singleton == null) {
                    singleton = new SynchronizedSingleton();
                }
            }
        }
        return singleton;
    }
}
