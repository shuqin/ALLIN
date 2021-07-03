package shared.utils;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

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
     * <p>
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

    public static <T> String objectToJson(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T jsonToObject(String src, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(src) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? src : MAPPER.readValue(src, typeReference));
        } catch (Exception e) {
            logger.warn("Parse Json to Object error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    public static <T> T jsonToObject(String src, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return MAPPER.readValue(src, javaType);
        } catch (Exception e) {
            logger.warn("Parse Json to Object error", e);
            throw new RuntimeException(e.getCause());
        }
    }

}
