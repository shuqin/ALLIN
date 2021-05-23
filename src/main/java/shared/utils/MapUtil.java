package shared.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapUtil {

  private MapUtil() {
  }

  private static class Element<K, V> implements Comparable<Element<K, V>> {

    private K key;
    private V value;

    public Element(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public int compareTo(Element<K, V> e) {

      try {
        Method m = value.getClass().getMethod("compareTo", value.getClass());
        return -((Integer) m.invoke(value, e.value));
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      return 0;  // dangerous! 当出现异常时，？？？
    }

    public String toString() {
      return "(" + key + " , " + value + ")";
    }


  }

  /**
   * printMap: 打印给定映射集合
   *
   * @param map 给定映射集合
   */

  public static <K, V> void printMap(Map<K, V> map) {
    Set<Map.Entry<K, V>> resultsEntry = map.entrySet();
    Iterator<Map.Entry<K, V>> iter = resultsEntry.iterator();
    while (iter.hasNext()) {
      Map.Entry<K, V> result = iter.next();
      System.out.println(result.getKey() + " = " + result.getValue());
    }
  }

  /**
   * sortMap: 根据映射值的大小，对映射集合进行排序。
   */
  public static <K, V> List<Element<K, V>> sortMap(Map<K, V> map) {
    List<Element<K, V>> elements = new ArrayList<Element<K, V>>();
    Set<Map.Entry<K, V>> mapEntries = map.entrySet();
    Iterator<Map.Entry<K, V>> iter = mapEntries.iterator();
    while (iter.hasNext()) {
      Map.Entry<K, V> entry = iter.next();
      elements.add(new Element<K, V>(entry.getKey(), entry.getValue()));
    }
    Collections.sort(elements);
    return elements;
  }

  /**
   * printList: 打印列表内容
   */
  public static <T> void print(List<T> list) {
    System.out.println("List: [");
    for (T t : list) {
      System.out.println(t);
    }
    System.out.println("]");
  }

  public static Object readVal(Map<String, Object> map, String path) {
    if (map == null || map.isEmpty()) { return  null; }
    return readVal(map, path.split("\\."));
  }

  private static Object readVal(Map<String, Object> map, String[] subpaths) {
    Object val = map;
    try {
      for (String subpath: subpaths) {
        if (val != null && val instanceof Map) {
          val = ((Map)val).get(subpath);
        }
        else {
          return null;
        }
      }
      return val == null ? null: val;
    } catch (Exception ex) {
      return null;
    }

  }

}

