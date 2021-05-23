package shared.utils;

import com.jayway.jsonpath.JsonPath;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 根据路径来读取 json 中的值
 * @Date 2021/5/23 1:44 下午
 * @Created by qinshu
 */
public class JsonPathUtil {

    /**
     * 对于正确JSON及存在的Path下获取到最终指定值并转成字符串，其他情况一律返回 null
     * @param json JSON串
     * @param path 点分隔的字段路径
     * @return 相应字段的字符串值
     */
    public static String readVal(String json, String path) {
        try {
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

    /**
     * 读取JSON字符串为MAP
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> readMap(String json) {
        return JsonUtil.toObject(json, HashMap.class);
    }
}
