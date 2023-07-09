package shared.utils;

import shared.constants.CommonValues;

import java.util.Collections;
import java.util.List;

/**
 * 字符串工具类
 * Created by qinshu on 2022/1/4
 */
public class StringUtils {

    private static final char UNDERLINE = '_';

    public static String getValue(String value) {
        return value == null ? org.apache.commons.lang3.StringUtils.EMPTY : value;
    }

    public static String getValue(Integer value) {
        return value == null ? org.apache.commons.lang3.StringUtils.EMPTY : value.toString();
    }

    /**
     * 字符串替换
     */
    public static String replace(final String text, final String regex, final String replacement) {
        if (text == null) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
        if (regex == null) {
            return text;
        }
        return text.replace(regex, getValue(replacement));
    }

    /**
     * 数组字符串转为字符串集合
     */
    public static List<String> parse(String arrayString) {
        if (org.apache.commons.lang3.StringUtils.isBlank(arrayString)) {
            return Collections.emptyList();
        }
        return JsonUtils.convertToList(arrayString, String.class);
    }


    /**
     * 驼峰 转 下划线
     *
     * @param str 驼峰
     * @return 下划线
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append(CommonValues.UNDER_LINE).append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 下划线 转 驼峰
     */
    public static String underlineToCamel(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        boolean capitalizeNextChar = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == UNDERLINE) {
                capitalizeNextChar = true;
            } else if (capitalizeNextChar) {
                sb.append(Character.toUpperCase(c));
                capitalizeNextChar = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }

        return sb.toString();
    }

}
