package cc.lovesq.dislock;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

import static avro.shaded.com.google.common.primitives.Ints.max;

public class RedisLock {

    private Jedis jedis = new Jedis("localhost");

    private static void basic(RedisLock redisLock, Jedis jedis) {

        redisLock.set("name", "qin", 1);
        redisLock.set("age", "29", Integer.MAX_VALUE);
        System.out.println(jedis.exists("name"));
        System.out.println(redisLock.jedis.mget("name", "age"));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }

        System.out.println(jedis.exists("name"));
        System.out.println(redisLock.jedis.mget("name", "age"));
    }

    public void set(String name, String value, int timestamp) {
        jedis.set(name, value);
        jedis.expire(name, timestamp);
    }

    public String get(String name) {
        return jedis.get(name);
    }

    public void setNx(String name, String key, int secs) {
        jedis.set(name, key, "NX", "EX", 1);
    }

    public Jedis getInstance() {
        return jedis;
    }

    public void sort(int[] list) {
        for (int i : list) {
            jedis.setbit("arr", i, true);
        }
        int max = max(list);
        for (int i = 0; i <= max; i++) {
            if (jedis.getbit("arr", i)) {
                System.out.printf(String.format("%d ", i));
            }
        }
    }

    public static class RedisLockTest {
        public static void main(String[] args) {
            RedisLock redisLock = new RedisLock();
            Jedis jedis = redisLock.getInstance();
            basic(redisLock, jedis);

            redisLock.sort(new int[]{5, 7, 1, 23, 26, 10, 28, 12});
        }
    }
}
