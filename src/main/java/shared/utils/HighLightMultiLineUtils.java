package shared.utils;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.constants.CommonValues;
import shared.dto.MatchedFileContent;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangli on 19-12-18.
 * 高亮文本工具类
 * NOTE:
 * 可以处理正则表达式匹配多行文本的高亮展示
 */
public class HighLightMultiLineUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HighLightMultiLineUtils.class);

    private static final Integer LINE_NUM = 10;
    private static final int MAX_REGEX_NUM = 10;

    /**
     * @param content 文本内容
     * @param regex   正则表达式
     * @return 高亮内容段落集
     */
    public static List<MatchedFileContent> highlight(String content, String regex) {
        return highlight(content, regex, MAX_REGEX_NUM, LINE_NUM);
    }

    private static List<MatchedFileContent> highlight(String content, String regex, int maxMatchNum, int lineNum) {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(regex)) {
            return Collections.emptyList();
        }

        content = content.replaceAll("\\r\\n", "\n");

        List<MatchedFileContent> partContentList = Lists.newArrayList();
        try {
            // 增加try catch，防止正则表达式语法错误导致的告警详情接口异常
            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
            Matcher matcher = pattern.matcher(content);
            int maxNum = maxMatchNum;
            while (matcher.find()) {
                RegexMatchPoint regexMatchPoint = new RegexMatchPoint(matcher.start(), matcher.end());
                partContentList.add(getPartContentMap(content, regexMatchPoint, lineNum));
                if (--maxNum == 0) {
                    break;
                }
            }
        } catch (Exception e) {
            LOG.error("failed to compile regex: " + regex, e);
        }

        return partContentList;
    }

    /**
     * 根据正则匹配获取高亮内容及起始行
     */
    private static MatchedFileContent getPartContentMap(String content, RegexMatchPoint m, int lineNum) {
        // 获取匹配内容在文件中的行数
        int startMatchLine = content.substring(0, m.getStart()).split("\\n").length;
        int endMatchLine = content.substring(0, m.getEnd()).split("\\n").length;
        // 高亮文件匹配内容
        String highlightContent = highlightOneRegexContent(content, m);
        // 截取匹配内容前后共20行(若匹配内容跨行，且大于10行，则从匹配的地方开始截取)
        String partContent = getPartContent(highlightContent, startMatchLine, endMatchLine);
        // 获取截取内容首行的行号
        int startLine = endMatchLine - lineNum + 1;
        //如果匹配的内容大于10行，则从最初匹配行开始，而不是固定的10行
        if (startMatchLine < startLine) {
            startLine = startMatchLine;
        }

        return new MatchedFileContent(Math.max(startLine, 1), partContent);
    }

    /**
     * 获取高亮行前后部分内容
     */
    private static String getPartContent(String content, Integer startMatchLine, Integer endMatchLine) {
        int start = StringUtils.ordinalIndexOf(content, "\n", endMatchLine - LINE_NUM);
        if (endMatchLine - startMatchLine > LINE_NUM) {
            start = StringUtils.ordinalIndexOf(content, "\n", startMatchLine - 1);
        }
        start = start < 0 ? 0 : start + 1;
        int end = StringUtils.ordinalIndexOf(content, "\n", endMatchLine + LINE_NUM);
        end = end < 0 ? content.length() : end;

        return content.substring(start, end);
    }

    /**
     * 高亮单个匹配的内容
     */
    private static String highlightOneRegexContent(String content, RegexMatchPoint point) {
        int start = 0;

        StringBuilder highlightContentSb = new StringBuilder();
        highlightContentSb.append(content, start, point.getStart()).append(CommonValues.HIGH_LIGHT_START)
                .append(content, point.getStart(), point.getEnd()).append(CommonValues.HIGH_LIGHT_END)
                .append(content.substring(point.getEnd()));

        return highlightContentSb.toString();
    }

    @Setter
    @Getter
    private static class RegexMatchPoint implements Comparable<RegexMatchPoint> {

        private Integer start;
        private Integer end;

        public RegexMatchPoint(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        //按开始位置排序
        @Override
        public int compareTo(RegexMatchPoint o) {
            if (start.compareTo(o.getStart()) == 0) {
                return end.compareTo(o.getEnd());
            } else {
                return start.compareTo(o.getStart());
            }
        }

        public RegexMatchPoint copy() {
            return new RegexMatchPoint(start, end);
        }
    }
}
