package zzz.study.algorithm.flowlimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

public class RateLimiterTest {

    private static Log log = LogFactory.getLog(RateLimiterTest.class);

    public static void main(String[] args) {
        log.info("-----------testBurstyRateLimiter---------");
        testBurstyRateLimiter();

        log.info("-----------testWarmupRateLimiter---------");
        testWarmupRateLimiter();
    }

    public static void testBurstyRateLimiter() {
        RateLimiter rateLimiter = RateLimiter.create(5);

        for (int i = 0; i < 20; i++) {
            rateLimiter.acquire();
            String info = String.format("%d*%d=%d", i, i, i * i);
            log.info(info);
        }

        RateLimiter rateLimiter2 = RateLimiter.create(5);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // do nothing
        }
        for (int i = 0; i < 20; i++) {
            rateLimiter2.acquire();
            String info = String.format("second %d*%d=%d", i, i, i * i);
            log.info(info);
        }
    }

    public static void testWarmupRateLimiter() {
        RateLimiter rateLimiter = RateLimiter.create(5, 10L, TimeUnit.SECONDS);

        for (int i = 0; i < 20; i++) {
            rateLimiter.acquire();
            String info = String.format("%d*%d=%d", i, i, i * i);
            log.info(info);
        }

        RateLimiter rateLimiter2 = RateLimiter.create(5, 10L, TimeUnit.SECONDS);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // do nothing
        }
        for (int i = 0; i < 20; i++) {
            rateLimiter2.acquire();
            String info = String.format("second %d*%d=%d", i, i, i * i);
            log.info(info);
        }
    }
}
