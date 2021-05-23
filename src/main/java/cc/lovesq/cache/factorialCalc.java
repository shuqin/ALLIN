package cc.lovesq.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class factorialCalc {

    private static Logger logger = LoggerFactory.getLogger(factorialCalc.class);

    static Random random = new Random(System.currentTimeMillis());

    public static void main(String[]args) {

        for (int i=1; i < 10; i++) {
            int num = random.nextInt(15);
            long start = System.nanoTime();
            long facRec = fac(num);
            long end = System.nanoTime();
            logger.info("fac({})={}", num, facRec);

            long start2 = System.nanoTime();
            long facWithCache = facWithCache(num);
            long end2 = System.nanoTime();
            logger.info("facWithCache({})={}", num, facWithCache);
            logCacheInfo(cache);

            logger.info("facRec cost: {}, facWithCache cost: {} us.", toMicros(end-start), toMicros(end2-start2));
        }
    }

    private static long toMicros(long nanos) {
        return TimeUnit.MICROSECONDS.toMicros(nanos);
    }

    private static void logCacheInfo(Cache<Integer, Long> cache) {
        logger.info("cache contents: {}", cache.asMap());
        logger.info("cache stat: {}", cache.stats());
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
