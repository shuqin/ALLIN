package zzz.study.utils;

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

}
