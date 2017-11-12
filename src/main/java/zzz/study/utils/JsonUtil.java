package zzz.study.utils;

import com.jayway.jsonpath.JsonPath;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    // 为保持对象版本兼容性,忽略未知的属性
    MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // 序列化的时候，跳过null值
    MAPPER.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    // date类型转化
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    MAPPER.setDateFormat(fmt);
  }

  /**
   * 将一个json字符串解码为java对象
   *
   * 注意：如果传入的字符串为null，那么返回的对象也为null
   *
   * @param json json字符串
   * @param cls  对象类型
   * @return 解析后的java对象
   * @throws RuntimeException 若解析json过程中发生了异常
   */
  public static <T> T toObject(String json, Class<T> cls) {
    if (json == null) {
      return null;
    }
    try {
      return MAPPER.readValue(json, cls);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 读取JSON字符串为MAP
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> readMap(String json) {
    return toObject(json, HashMap.class);
  }

  public static String readVal(String json, String path) {
    if (json == null || path == null) {
      return null;
    }
    Map<String,Object> map = readMap(json);
    if (map == null) {
      throw new IllegalArgumentException("parse json failed: " + json);
    }
    String[] subpaths = path.split("\\.");
    return readVal(map, subpaths);
  }

  private static String readVal(Map<String, Object> map, String[] subpaths) {
    Object val = map;
    try {
      for (String subpath: subpaths) {
        if (val != null && val instanceof Map) {
          val = ((Map)val).get(subpath);
        }
        else {
          throw new IllegalArgumentException("subpath may not exists in " + map);
        }
      }
      return val == null ? null: val.toString();
    } catch (Exception ex) {
      return null;
    }

  }

  public static Object readValUsingJsonPath(String json, String path) {
    if (json == null || path == null) {
      return null;
    }
    return JsonPath.read(json, path);
  }

}
