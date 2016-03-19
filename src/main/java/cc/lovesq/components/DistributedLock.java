package cc.lovesq.components;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public interface DistributedLock {
    Lock lock(String key, long time, TimeUnit timeUnit);

}
