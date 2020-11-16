package cc.lovesq.cache;

import cc.lovesq.annotations.Timecost;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class factorialCalc {

    private static Log log = LogFactory.getLog(factorialCalc.class);

    static Random random = new Random(System.currentTimeMillis());

    public static void main(String[]args) {

        for (int i=1; i < 10; i++) {
            int num = random.nextInt(15);
            long start = System.nanoTime();
            long facRec = fac(num);
            long end = System.nanoTime();
            String info = String.format("fac(%d)=%d", num, facRec);
            log.info(info);

            long start2 = System.nanoTime();
            long facWithCache = facWithCache(num);
            long end2 = System.nanoTime();
            String info2 = String.format("facWithCache(%d)=%d", num, facWithCache);
            log.info(info2);
            printCacheInfo(cache);

            log.info("facRec cost: " + toMicros(end-start) + " " + "facWithCache cost: " +  toMicros(end2-start2) + " us");
        }
    }

    private static long toMicros(long nanos) {
        return TimeUnit.MICROSECONDS.toMicros(nanos);
    }

    private static void printCacheInfo(Cache<Integer, Long> cache) {
        log.info("cache contents: " + cache.asMap());
        log.info("cache stat: " + cache.stats());
    }

    public static long fac(int n) {
        if (n <= 1) return 1;
        return n * fac(n-1);
    }

    private static Cache<Integer, Long> cache = CacheBuilder.newBuilder().recordStats().build();

    public static long facWithCache(int n) {
        if (n <= 1) {
            cache.put(1, 1L);
            return 1L;
        }
        Long facN_1 = cache.getIfPresent(n-1);
        if (facN_1 == null) {
            facN_1 = facWithCache(n-1);
        }
        long facN = n * facN_1;
        cache.put(n, facN);
        return facN;
    }
}
