package shared.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.constants.CommonValues;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER_WITHOUT_NULL = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER_SNAKE_CASE = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER_ALLOW_BACKSLASH = new ObjectMapper();

    public static ObjectMapper mapper() {
        return OBJECT_MAPPER;
    }

    public static <T> List<T> readList(String input, Class<T> clz) {
        if (input == null) {
            return null;
        }

        try {
            JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(
                    LinkedList.class, clz);
            return OBJECT_MAPPER.readValue(input, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> convertToList(Object from, Class<T> elemClass) {
        if (from == null) {
            return null;
        }
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, elemClass);
        if (from instanceof String) {
            try {
                return OBJECT_MAPPER.readValue((String) from, type);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
                return null;
            }
        } else {
            try {
                return OBJECT_MAPPER.convertValue(from, type);
            } catch (IllegalArgumentException e) {
                LOG.error(e.getMessage(), e);
                return null;
            }
        }
    }

    public static <T, R> List<R> convertList(List<T> from, Class<R> elemClass) {
        if (from == null) {
            return null;
        }

        return StreamUtils.map(from, e -> convert(e, elemClass));
    }

    public static <T> T convertFromString(String from, Class<T> cls) {
        if (from == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(from, cls);
        } catch (IOException e) {
            LOG.error(
                    String.format("Failed to convert String %s to class %s", from, cls),
                    e);
            return null;
        }
    }

    public static <T> T convertFromBytes(byte[] from, Class<T> cls) {
        if (from == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(from, cls);
        } catch (IOException e) {
            LOG.error(String.format("Failed to convert String %s to class %s", from, cls), e);
            return null;
        }
    }

    public static <T> String convertToString(T from) {
        if (from == null) {
            return CommonValues.EMPTY_STRING;
        }
        if (from instanceof String) {
            return (String) from;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(from);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to convert to String", e);
            return CommonValues.EMPTY_STRING;
        }
    }

    public static <T> String convertToSnakeCaseString(T from) {
        if (from == null) {
            return CommonValues.EMPTY_STRING;
        }
        if (from instanceof String) {
            return (String) from;
        }

        try {
            //OBJECT_MAPPER_SNAKE_CASE.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return OBJECT_MAPPER_SNAKE_CASE.writeValueAsString(from);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to convert to String", e);
            return CommonValues.EMPTY_STRING;
        }
    }

    /**
     * 序列化对象为json字符串，不包含值为null的字段
     */
    public static <T> String convertToStringWithoutNull(T from) {
        if (from == null) {
            return CommonValues.EMPTY_STRING;
        }
        if (from instanceof String) {
            return (String) from;
        }
        try {
            return OBJECT_MAPPER_WITHOUT_NULL.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(from);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to convert to String", e);
            return CommonValues.EMPTY_STRING;
        }
    }

    public static <T> byte[] convertToBytes(T from) {
        if (from == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.writeValueAsBytes(from);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to convert to String", e);
            return null;
        }
    }

    public static <T, R> R convert(T from, Class<R> cls) {
        if (from == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.convertValue(from, cls);
        } catch (IllegalArgumentException e) {
            LOG.error(String.format("Failed to convert value to class %s", cls), e);
            return null;
        }
    }

    public static <T, R> R convertAllowBackslash(String from, Class<R> cls) {
        if (from == null) {
            return null;
        }
        OBJECT_MAPPER_ALLOW_BACKSLASH.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        try {
            return OBJECT_MAPPER_ALLOW_BACKSLASH.readValue(from, cls);
        } catch (IOException e) {
            LOG.error(String.format("Failed to convert value to class %s", cls), e);
            return null;
        }
    }

    public static JsonNode readTree(String value) {
        try {
            return OBJECT_MAPPER.readTree(value);
        } catch (IOException e) {
            LOG.error(String.format("Fail to read value: %s", value), e);
            return null;
        }
    }

    public static JsonNode readTree(byte[] bytes) {
        try {
            return OBJECT_MAPPER.readTree(bytes);
        } catch (IOException e) {
            LOG.error(String.format("Fail to read value: %s", new String(bytes, StandardCharsets.UTF_8)), e);
            return null;
        }
    }

    public static String treeToString(JsonNode tree) {
        try {
            return OBJECT_MAPPER.treeToValue(tree, String.class);
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    public static String readStringField(String content, String field) {
        try (JsonParser parser = mapper().getFactory().createParser(content)) {
            return doReadStringField(parser, field);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readStringField(JsonNode parent, String field, String def) {
        JsonNode node = parent.get(field);
        if (node != null) {
            return node.asText(def);
        } else {
            return def;
        }
    }

    public static int readIntField(JsonNode parent, String field, int def) {
        JsonNode node = parent.get(field);
        if (node != null) {
            return node.asInt(def);
        } else {
            return def;
        }
    }

    public static <T> List<T> parse(JsonNode jsonBody, Class<T> cls) {
        List<T> resultList = Lists.newArrayList();
        if (jsonBody.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonBody;
            for (JsonNode nodeData : arrayNode) {
                T agentAggregatedDetections = convert(nodeData, cls);
                resultList.add(agentAggregatedDetections);
            }
        } else {
            T obj = convert(jsonBody, cls);
            resultList.add(obj);
        }
        return resultList;
    }

    private static String doReadStringField(JsonParser parser, String field) throws IOException {
        JsonToken token = parser.nextToken();
        if (token != JsonToken.START_OBJECT) {
            return null;
        }

        token = parser.nextToken();
        while (token != null && token != JsonToken.END_OBJECT) {
            String name = parser.getCurrentName();
            if (field.equals(name)) {
                parser.nextToken();
                return parser.getText();
            }

            // 只读第一层
            if (token.isStructStart()) {
                parser.skipChildren();
            }

            token = parser.nextToken();
        }

        return null;
    }

    public static JsonNode readField(JsonNode kb, String path) {
        String[] fields = path.split("\\.");

        JsonNode value = null;
        for (String field : fields) {
            value = getField(value == null ? kb : value, field);
            if (value == null) {
                break;
            }
        }

        return value;
    }

    private static JsonNode getField(JsonNode root, String field) {
        if (root == null) {
            return null;
        }
        return root.get(field);
    }

    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return OBJECT_MAPPER.createArrayNode();
    }

    public static <T> ArrayNode convertListToArrayNode(List<T> list) {
        return OBJECT_MAPPER.valueToTree(list);
    }

    private static final org.codehaus.jackson.map.ObjectMapper MAPPER = new org.codehaus.jackson.map.ObjectMapper();

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
            LOG.warn("Parse Json to Object error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    public static <T> T jsonToObject(String src, Class<?> collectionClass, Class<?>... elementClasses) {
        org.codehaus.jackson.type.JavaType javaType = MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return MAPPER.readValue(src, javaType);
        } catch (Exception e) {
            LOG.warn("Parse Json to Object error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    public static Map readMap(String studentInfoStr) {
        return toObject(studentInfoStr, Map.class);
    }

}
