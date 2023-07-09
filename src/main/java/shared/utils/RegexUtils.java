package shared.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * RegexUtils:
 * created by qin.shu 2023/6/27
 */
public class RegexUtils {

    /**
     * 将关键字转换为正则表达式
     * 3.x 是通常这种方式来匹配高亮关键字的
     */
    public static String escapeRegexSpecialWord(String keyword) {
        if (StringUtils.isNoneBlank(keyword)) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }
}
