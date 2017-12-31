package zzz.study.datastructure.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TransferUtil {

  public static final char UNDERLINE='_';

  public static String camelToUnderline(String origin){
    return stringProcess(
        origin, (prev, c) -> {
          if (Character.isLowerCase(prev) && Character.isUpperCase(c)) {
            return "" + UNDERLINE + Character.toLowerCase(c);
          }
          return ""+c;
        }
    );
  }

  public static String underlineToCamel(String origin) {
    return stringProcess(
        origin, (prev, c) -> {
          if (prev == '_' && Character.isLowerCase(c)) {
            return "" + Character.toUpperCase(c);
          }
          if (c == '_') {
            return "";
          }
          return ""+c;
        }
    );
  }

  public static String stringProcess(String origin, BiFunction<Character, Character, String> convertFunc) {
    if (origin==null||"".equals(origin.trim())){
      return "";
    }
    String newOrigin = "0" + origin;
    StringBuilder sb=new StringBuilder();
    for (int i = 0; i < newOrigin.length()-1; i++) {
      char prev = newOrigin.charAt(i);
      char c=newOrigin.charAt(i+1);
      sb.append(convertFunc.apply(prev,c));
    }
    return sb.toString();
  }

  public static void tranferKeyToUnderline(Map<String,Object> map,
                                           Map<String,Object> resultMap,
                                           Set<String> ignoreKeys) {
    Set<Map.Entry<String,Object>> entries = map.entrySet();
    for (Map.Entry<String, Object> entry: entries) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (ignoreKeys.contains(key)) {
        resultMap.put(key, value);
        continue;
      }
      String newkey = camelToUnderline(key);
      if ( (value instanceof List) ) {
        List newList = buildValueList(
            (List) value, ignoreKeys,
            (m, keys) -> {
              Map subResultMap = new HashMap();
              tranferKeyToUnderline((Map) m, subResultMap, ignoreKeys);
              return subResultMap;
            });
        resultMap.put(newkey, newList);
      }
      else if (value instanceof Map) {
        Map<String, Object> subResultMap = new HashMap<String,Object>();
        tranferKeyToUnderline((Map)value, subResultMap, ignoreKeys);
        resultMap.put(newkey, subResultMap);
      }
      else {
        resultMap.put(newkey, value);
      }
    }
  }

  public static Map<String,Object> tranferKeyToUnderline2(Map<String,Object> map,
                                                          Set<String> ignoreKeys) {
    Set<Map.Entry<String,Object>> entries = map.entrySet();
    Map<String,Object> resultMap = new HashMap<String,Object>();
    for (Map.Entry<String, Object> entry: entries) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (ignoreKeys.contains(key)) {
        resultMap.put(key, value);
        continue;
      }
      String newkey = camelToUnderline(key);
      if ( (value instanceof List) ) {
        List valList = buildValueList((List)value, ignoreKeys,
                                      (m, keys) -> tranferKeyToUnderline2(m, keys));
        resultMap.put(newkey, valList);
      }
      else if (value instanceof Map) {
        Map<String, Object> subResultMap =  tranferKeyToUnderline2((Map)value, ignoreKeys);
        resultMap.put(newkey, subResultMap);
      }
      else {
        resultMap.put(newkey, value);
      }
    }
    return resultMap;
  }

  public static List buildValueList(List valList, Set<String> ignoreKeys,
                                    BiFunction<Map, Set, Map> transferFunc) {
    if (valList == null || valList.size() == 0) {
      return valList;
    }
    Object first = valList.get(0);
    if (!(first instanceof List) && !(first instanceof Map)) {
      return valList;
    }
    List newList = new ArrayList();
    for (Object val: valList) {
      Map<String,Object> subResultMap = transferFunc.apply((Map) val, ignoreKeys);
      newList.add(subResultMap);
    }
    return newList;
  }

  public static Map<String,Object> generalMapProcess(Map<String,Object> map,
                                                     Function<String, String> keyFunc,
                                                     Set<String> ignoreKeys) {
    Map<String,Object> resultMap = new HashMap<String,Object>();
    map.forEach(
        (key, value) -> {
          if (ignoreKeys.contains(key)) {
            resultMap.put(key, value);
          }
          else {
            String newkey = keyFunc.apply(key);
            if ( (value instanceof List) ) {
              resultMap.put(keyFunc.apply(key),
                            buildValueList((List) value, ignoreKeys,
                                           (m, keys) -> generalMapProcess(m, keyFunc, ignoreKeys)));
            }
            else if (value instanceof Map) {
              Map<String, Object> subResultMap =  generalMapProcess((Map) value, keyFunc, ignoreKeys);
              resultMap.put(newkey, subResultMap);
            }
            else {
              resultMap.put(keyFunc.apply(key), value);
            }
          }
        }
    );
    return resultMap;
  }



}
