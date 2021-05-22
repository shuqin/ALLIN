package shared.utils;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

  private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

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
      throw new RuntimeException(e.getCause());
    }
  }

  public static <T> String objectToJson(T obj){
    if(obj == null){
      return null;
    }
    try {
      return obj instanceof String ? (String) obj : MAPPER.writeValueAsString(obj);
    } catch (Exception e) {
      return null;
    }
  }

  public static <T> T jsonToObject(String src, TypeReference<T> typeReference){
    if(StringUtils.isEmpty(src) || typeReference == null){
      return null;
    }
    try {
      return (T)(typeReference.getType().equals(String.class) ? src : MAPPER.readValue(src, typeReference));
    } catch (Exception e) {
      logger.warn("Parse Json to Object error",e);
      throw new RuntimeException(e.getCause());
    }
  }

  public static <T> T jsonToObject(String src, Class<?> collectionClass,Class<?>... elementClasses){
    JavaType javaType = MAPPER.getTypeFactory().constructParametricType(collectionClass,elementClasses);
    try {
      return MAPPER.readValue(src,javaType);
    } catch (Exception e) {
      logger.warn("Parse Json to Object error",e);
      throw new RuntimeException(e.getCause());
    }
  }


  /**
   * 读取JSON字符串为MAP
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> readMap(String json) {
    return toObject(json, HashMap.class);
  }

  /**
   * 对于正确JSON及存在的Path下获取到最终指定值并转成字符串，其他情况一律返回 null
   * @param json JSON串
   * @param path 点分隔的字段路径
   * @return 相应字段的字符串值
   */
  public static String readVal(String json, String path) {
    if (json == null || path == null) {
      return null;
    }
    Map<String,Object> map = readMap(json);
    if (map == null) {
      // log.warn("parse json failed: " + json);
      return null;
    }
    String[] subpaths = path.split("\\.");
    return readVal(map, subpaths);
  }

  private static String readVal(Map<String, Object> map, String path) {
    return readVal(map, path.split("\\."));
  }

  private static String readVal(Map<String, Object> map, String[] subpaths) {
    Object val = map;
    try {
      for (String subpath: subpaths) {
        if (val != null && val instanceof Map) {
          val = ((Map)val).get(subpath);
        }
        else {
          // log.warn("subpath may not exists in " + map);
          return null;
        }
      }
      return val == null ? null: val.toString();
    } catch (Exception ex) {
      return null;
    }

  }

  public static String readValUsingJsonPath(String json, String path) {
    if (json == null || path == null) {
      return null;
    }
    try {
      Object val = JsonPath.read(json, "$." + path);
      return val == null ? null : val.toString();
    } catch (Exception ex) {
      return null;
    }
  }

}
