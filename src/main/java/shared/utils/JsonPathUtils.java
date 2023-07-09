package shared.utils;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 根据路径来读取 json 中的值
 * @Date 2021/5/23 1:44 下午
 * @Created by qinshu
 */
public class JsonPathUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonPathUtils.class);

    private static final String[] SPECIAL_WORDS = {"\\$.", "\\[\\*]"}; //特殊字符

    private static final String EMPTY_WORD = "";

    /**
     * 对于正确JSON及存在的Path下获取到最终指定值并转成字符串，其他情况一律返回 null
     *
     * @param json JSON串
     * @param path 点分隔的字段路径
     * @return 相应字段的字符串值
     */
    public static String readVal(String json, String path) {
        try {
            if (json == null || path == null) {
                return null;
            }
            Map<String, Object> map = readMap(json);
            if (map == null) {
                // log.warn("parse json failed: " + json);
                return null;
            }
            String[] subpaths = path.split("\\.");
            return readVal(map, subpaths);
        } catch (Exception ex) {
            return null;
        }

    }

    private static String readVal(Map<String, Object> map, String path) {
        return readVal(map, path.split("\\."));
    }

    private static String readVal(Map<String, Object> map, String[] subpaths) {
        Object val = map;
        try {
            for (String subpath : subpaths) {
                if (val != null && val instanceof Map) {
                    val = ((Map) val).get(subpath);
                } else {
                    // log.warn("subpath may not exists in " + map);
                    return null;
                }
            }
            return val == null ? null : val.toString();
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

    public static String readValByJsonPath(String json, String path) {
        if (json == null || path == null) {
            return null;
        }
        try {
            Object val = JsonPath.read(json, path);
            return val == null ? null : val.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 读取JSON字符串为MAP
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> readMap(String json) {
        return JsonUtils.toObject(json, HashMap.class);
    }

    /**
     * 从obj对象中解析出field的值
     *
     * @param obj javabean对象
     * @param expression jsonpath表达式。示例：
     *                   1、$.sockets[*].srcIp   取出json中sockets(集合)下所有srcIp的值
     *                   2、$.processInfo.pname  取出json中processInfo(对象)下pname的值
     *                   3、$.agentId            取出json中agentId下的值
     * @return 如果expression格式正确且存在则返回值，否则返回null
     */
    public static Object readValue(Object obj, String expression) {
        try {
            //1、将obj对象转为json字符串
            String jsonStr = JsonUtils.convertToString(obj);
            //2、解析字符串
            //Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonStr);
            //return getCache(expression).read(document);
            return null;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 替换掉jsonpath表达式中的特殊字符
     *
     * @param expression jsonpath表达式。示例：
     *                   1、$.sockets[*].srcIp   返回 sockets.srcIp
     *                   2、$.processInfo.pname  返回 processInfo.pname
     *                   3、$.agentId            返回 agentId
     */
    public static String replaceSpecial(String expression) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(expression)) {
            return expression;
        }
        for (String specialStr : SPECIAL_WORDS) {
            expression = StringUtils.replaceAll(expression, specialStr, EMPTY_WORD);
        }
        return expression;
    }
}
