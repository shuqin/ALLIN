package shared.utils;

public abstract class ThreadUtils {
    private static final int DEFAULT_THREADS_NUM;
    static {
        DEFAULT_THREADS_NUM = Math.max(1, Runtime.getRuntime().availableProcessors() * 2);
    }

    public static int defaultThreadNum() {
        return DEFAULT_THREADS_NUM;
    }

    public static int defaultThreadNum(int max) {
        return Math.min(DEFAULT_THREADS_NUM, max);
    }
}
