package cc.lovesq.components;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Component("redissonLock")
public class RedissonLock implements DistributedLock {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public Lock lock(String key, long time, TimeUnit timeUnit) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(time, timeUnit);
        return lock;
    }

}
