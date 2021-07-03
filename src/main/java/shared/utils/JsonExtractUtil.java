package shared.utils;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang.StringUtils;

import java.util.Map;


public class JsonExtractUtil {

    private static final String BIZ_ORDER_ATTRIBUTE = "BIZ_ORDER_ATTRIBUTE";
    private static final String BIZ_ITEM_ATTRIBUTE = "BIZ_ITEM_ATTRIBUTE";

    private static JsonPath BIZ_ORDER_ATTRIBUTE_PATH = JsonPath.compile("$." + BIZ_ORDER_ATTRIBUTE);
    private static JsonPath BIZ_ITEM_ATTRIBUTE_PATH = JsonPath.compile("$." + BIZ_ITEM_ATTRIBUTE);

    public static String extractItemBizAttributeString(String json, String nestField) {
        return extractString(BIZ_ITEM_ATTRIBUTE_PATH.read(json), nestField);
    }

    public static Object extractItemBizAttribute(String json, String nestField) {
        return extract(BIZ_ITEM_ATTRIBUTE_PATH.read(json), nestField);
    }

    public static String extractOrderBizAttributeString(String json, String nestField) {
        return extractString(BIZ_ORDER_ATTRIBUTE_PATH.read(json), nestField);
    }

    public static Object extractOrderBizAttribute(String json, String nestField) {
        return extract(BIZ_ORDER_ATTRIBUTE_PATH.read(json), nestField);
    }

    /**
     * 从 Map 中析取指定字段的值
     *
     * @param extra     map
     * @param topField  顶层字段
     * @param nestField 顶层字段嵌套下的二级字段
     *                  <p>
     *                  eg.
     *                  Map{"IS_MEMBER":"true","PRICE":{"originAmount":4990,"totalAmount":4990},"BIZ_ORDER_EXTEND":"{\"CART_INFO\":\"fromRetail\"}"}
     *                  PRICE 是顶层字段, totalAmount 是二级字段
     */
    public static Object extract(Map<String, Object> extra, String topField, String nestField) {
        if (extra == null || extra.isEmpty() || org.apache.commons.lang.StringUtils.isBlank(topField)) {
            return null;
        }
        if (org.apache.commons.lang.StringUtils.isBlank(nestField)) {
            return extra.get(topField);
        }
        return extract(toString(extra.get(topField)), nestField);
    }

    public static Object extract(String json, String field) {
        return extract(json, field, null, false);
    }

    public static Object extract(String json, String field, String nestField) {
        return extract(json, field, nestField, false);
    }

    /**
     * 从 JSON 串中析取指定字段的值
     *
     * @param json             json 串
     * @param topField         顶层字段
     * @param nestField        顶层字段嵌套下的二级字段
     * @param isTopFieldString 顶层字段是否是一个 json 形式的字符串，比如 BIZ_ORDER_EXTEND
     *                         <p>
     *                         eg.
     *                         {"IS_MEMBER":"true","PRICE":{"originAmount":4990,"totalAmount":4990},"BIZ_ORDER_EXTEND":"{\"CART_INFO\":\"fromRetail\"}"}
     *                         PRICE 是顶层字段, totalAmount 是二级字段
     */
    public static Object extract(String json, String topField, String nestField, boolean isTopFieldString) {
        try {
            if (org.apache.commons.lang.StringUtils.isBlank(json) || org.apache.commons.lang.StringUtils.isBlank(topField)) {
                return null;
            }
            if (StringUtils.isBlank(nestField)) {
                return JsonPath.read(json, "$." + topField);
            }
            if (isTopFieldString) {
                return extractWhenTopFieldIsString(json, topField, nestField);
            }
            return JsonPath.read(json, "$." + topField + "." + nestField);
        } catch (Exception ex) {
            // add log
            try {
                // 这里做个兜底，应对 isTopFieldString 传错的情形
                return extractWhenTopFieldIsString(json, topField, nestField);
            } catch (Exception e) {
                // add log
            }
            return null;
        }

    }

    private static Object extractWhenTopFieldIsString(String json, String field, String nestField) {
        Object obj = JsonPath.read(json, "$." + field);
        return JsonPath.read(obj.toString(), "$." + nestField);
    }

    public static String extractString(Map<String, Object> map, String topField, String nestField) {
        return toString(extract(map, topField, nestField));
    }

    public static String extractString(String json, String field) {
        return extractString(json, field, null, false);
    }

    public static Object extractString(String json, String field, String nestField) {
        return extract(json, field, nestField, false);
    }

    public static String extractString(String json, String field, String nestField, boolean isTopFieldString) {
        Object obj = extract(json, field, nestField, isTopFieldString);
        return toString(obj);
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

}