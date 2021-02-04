package cc.lovesq.util;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Date 2021/2/4 9:00 下午
 * @Created by qinshu
 */
public class TimeUtil {

    public static void sleepInMs(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            //
        }
    }

    public static void sleepInSecs(long secs) {
        try {
            TimeUnit.SECONDS.sleep(secs);
        } catch (InterruptedException e) {
            //
        }
    }
}
