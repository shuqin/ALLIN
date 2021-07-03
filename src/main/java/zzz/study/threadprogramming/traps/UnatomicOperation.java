package zzz.study.threadprogramming.traps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnatomicOperation {

    private Map<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        UnatomicOperation unatomicOperation = new UnatomicOperation();
        ThreadStarter.startMultiThreads(
                (ti) -> {
                    unatomicOperation.nonSafeAdd("key");
                    System.out.println(ti + ":" + unatomicOperation.get("key"));
                }
        );
        System.out.println("final: " + unatomicOperation.get("key"));
    }

    public void add(String key) {
        Integer value = map.get(key);
        if (value == null) {
            map.put(key, 1);
        } else {
            map.put(key, value + 1);
        }
    }

    public void nonSafeAdd(String key) {
        map.put(key, map.putIfAbsent(key, 1) + 1);
    }

    public Integer get(String key) {
        return map.get(key);
    }
}
