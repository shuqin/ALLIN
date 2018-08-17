package zzz.study.algorithm.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BizObjects<T, K> {

  private Class<T> cls;
  private Map<K, List<T>> map;
  private Function<T, K> keyFunc;

  public BizObjects(Class<T> cls, Map<K,List<T>> map, Function<T,K> keyFunc) {
    this.cls = cls;
    this.map = (map != null ? map : new HashMap<>());
    this.keyFunc = keyFunc;
  }

  public void add(T t) {
    K key = keyFunc.apply(t);
    List<T> objs = map.getOrDefault(key, new ArrayList<>());
    objs.add(t);
    map.put(key, objs);
  }

  public Class<T> getObjectClass() {
    return cls;
  }

  public List<T> get(K key) {
    return map.get(key);
  }

  public T getSingle(K key) {
    return (map != null && map.containsKey(key) && map.get(key).size() > 0) ? map.get(key).get(0) : null;
  }

  public Map<K, List<T>> getObjects() {
    return Collections.unmodifiableMap(map);
  }

}
