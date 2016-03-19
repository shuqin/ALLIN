package zzz.study.algorithm.dividing;

import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ConsistentHashLoadBalance {

    public static void main(String[] args) {
        List<String> nodes = Lists.newArrayList("192.168.1.1", "192.168.2.25", "192.168.3.255", "255.255.1.1");

        ConsistentHashSelector<String> selector = new ConsistentHashSelector(nodes, Function.identity());
        test(selector);

        selector.addNode("8.8.8.8");
        test(selector);
    }


    private static void test(ConsistentHashSelector<String> selector) {
        Map<String, List<Integer>> map = new HashMap<>();
        for (int i = 1; i < 16000; i += 1) {
            String node = selector.select(String.valueOf(i));
            List<Integer> objs = map.getOrDefault(node, new ArrayList<>());
            objs.add(i);
            map.put(node, objs);
        }
        map.forEach(
                (key, values) -> {
                    System.out.println(key + " contains: " + values.size() + " --- " + values);
                }
        );
    }

    private static final class ConsistentHashSelector<T> {

        private final TreeMap<Long, T> virtualNodes;

        private final int replicaNumber = 160;

        private final Function<T, String> keyFunc;

        ConsistentHashSelector(List<T> nodes, Function<T, String> keyFunc) {
            this.virtualNodes = new TreeMap<Long, T>();
            this.keyFunc = keyFunc;
            assert keyFunc != null;
            for (T node : nodes) {
                addNode(node);
            }
        }

        public boolean addNode(T node) {
            opNode(node, (m, no) -> virtualNodes.put(m, no));
            return true;
        }

        public boolean removeNode(T node) {
            opNode(node, (m, no) -> virtualNodes.remove(m));
            return true;
        }

        public void opNode(T node, BiConsumer<Long, T> hashFunc) {
            String key = keyFunc.apply(node);
            for (int i = 0; i < replicaNumber / 4; i++) {
                byte[] digest = md5(key + i);
                for (int h = 0; h < 4; h++) {
                    long m = hash(digest, h);
                    hashFunc.accept(m, node);
                }
            }
        }

        public T select(String key) {
            byte[] digest = md5(key);
            return selectForKey(hash(digest, 0));
        }

        private T selectForKey(long hash) {
            Map.Entry<Long, T> entry = virtualNodes.ceilingEntry(hash);
            if (entry == null) {
                entry = virtualNodes.firstEntry();
            }
            return entry.getValue();
        }

        private long hash(byte[] digest, int number) {
            return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                    | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                    | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                    | (digest[number * 4] & 0xFF))
                    & 0xFFFFFFFFL;
        }

        private byte[] md5(String value) {
            MessageDigest md5;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            md5.reset();
            byte[] bytes;
            try {
                bytes = value.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            md5.update(bytes);
            return md5.digest();
        }

    }

}
