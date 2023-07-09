package shared.utils;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.constants.CommonValues;
import shared.dto.MatchedFileContent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 高亮文本展示工具类
 * Created by qinshu on 2021/12/31
 * NOTE:
 * 无法处理正则表达式匹配多行文本的高亮展示
 */
public class HighLightUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HighLightUtils.class);

    /**
     * 高亮展示前后的行数
     */
    private static final Integer HIGHLIGHT_LINE_NUM = 5;

    /**
     * 最大匹配多少次
     */
    private static final int MAX_REGEX_NUM = 10;

    /**
     * @param content 文本内容
     * @param regex   正则表达式
     * @return 高亮内容段落集
     */
    public static List<MatchedFileContent> highlight(String content, String regex) {
        return highlight(content, regex, MAX_REGEX_NUM, HIGHLIGHT_LINE_NUM);
    }

    /**
     * @param base64Content 文本内容（base64编码后的文本）
     * @param regex         正则表达式
     * @return 高亮内容段落集
     * NOTE:
     * 1.  highlight 无法处理一个正则必须用多行文本匹配的高亮展示；
     * 2.  HighLightMultiLineUtils.highlightBase64 当一个正则匹配多个单行文本时会重复展示
     * 因此，策略是，先用 highlight 处理，如果匹配结果为空，再用 HighLightMultiLineUtils 处理一遍。
     */
    public static List<MatchedFileContent> highlightBase64(String base64Content, String regex) {
        if (StringUtils.isEmpty(base64Content)) {
            return Collections.emptyList();
        }
        String originContent = Base64Utils.decodeContent(base64Content);
        List<MatchedFileContent> matchedFileContents = highlight(originContent, regex);
        if (CollectionUtils.isNotEmpty(matchedFileContents)) {
            return matchedFileContents;
        }
        return HighLightMultiLineUtils.highlight(originContent, regex);
    }

    public static List<MatchedFileContent> highlight(String content, String regex, int maxMatchNum, int highlightLineNum) {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(regex)) {
            return Collections.emptyList();
        }

        content = content.replaceAll("\\r\\n", "\n");

        List<String> allLines = Arrays.asList(content.split("\n"));

        List<RegexMatchPoint> regexMatchPoints = Lists.newArrayList();
        try {
            // 增加try catch，防止正则表达式语法错误导致的告警详情接口异常
            Pattern pattern = Pattern.compile(regex);
            regexMatchPoints = findAllRegexMatches(allLines, pattern);
        } catch (Exception e) {
            LOG.error("failed to compile regex: " + regex, e);
        }

        // 按行号分组，匹配高亮展示，因为单行多个匹配的高亮需要单行展示，分开后合并比较麻烦
        Map<Integer, List<RegexMatchPoint>> regexMatchPointMap = StreamUtils.group(regexMatchPoints, RegexMatchPoint::getLineNo);

        // highLightLineMap: [行号，高亮行]
        Map<Integer, String> highLightLineMap = new HashMap<>();
        regexMatchPointMap.forEach((lineNo, matchPointsOfLine) -> highLightLineMap.put(lineNo, highlightOneLineContent(allLines.get(lineNo), matchPointsOfLine)));

        List<MatchedFileContent> partContentList = merge(highLightLineMap, allLines, highlightLineNum);
        return partContentList.subList(0, Math.min(partContentList.size(), maxMatchNum));
    }

    private static List<MatchedFileContent> merge(Map<Integer, String> highLightLineMap, List<String> allLines, int highlightLineNum) {
        // 按行号排序
        List<Integer> highLightLineNos = Lists.newArrayList(highLightLineMap.keySet());
        Collections.sort(highLightLineNos);

        // 计算需要展示的行号
        List<MatchedFileLine> matchedFileLines = Lists.newArrayList();
        for (Integer highLineNo : highLightLineNos) {
            if (!exist(matchedFileLines, highLineNo)) {
                int startLine = highLineNo - highlightLineNum;
                if (startLine < 0) {
                    startLine = 0;
                }
                int endLine = highLineNo + highlightLineNum;
                matchedFileLines.add(new MatchedFileLine(startLine, endLine));
            }
        }

        return StreamUtils.map(matchedFileLines, fileLine -> getMatchedFileContent(highLightLineMap, allLines, fileLine));
    }

    /**
     * 获取指定行号的行内容
     */
    private static String getLine(Map<Integer, String> highLightLineMap, List<String> allLines, Integer lineNo) {
        String highLightLine = highLightLineMap.get(lineNo);
        return highLightLine != null ? highLightLine : allLines.get(lineNo);
    }

    private static boolean exist(List<MatchedFileLine> matchedFileLines, Integer lineNo) {
        if (CollectionUtils.isEmpty(matchedFileLines)) {
            return false;
        }
        MatchedFileLine lastFileLine = matchedFileLines.get(matchedFileLines.size() - 1);
        return exist(lastFileLine, lineNo);
    }

    private static boolean exist(MatchedFileLine matchedFileLine, Integer lineNo) {
        return lineNo >= matchedFileLine.getStartLine() && lineNo < matchedFileLine.getEndLine();
    }

    /**
     * 根据起始行号获取
     *
     * @param highLightLineMap 高亮行
     * @param allLines         文件所有行
     * @param fileLine         匹配内容上下文行号
     * @return 匹配内容上下文及起始行号
     */
    private static MatchedFileContent getMatchedFileContent(Map<Integer, String> highLightLineMap, List<String> allLines, MatchedFileLine fileLine) {
        StringBuilder partContentBuilder = new StringBuilder();
        for (int i = fileLine.getStartLine(); i <= fileLine.getEndLine() && i < allLines.size(); i++) {
            partContentBuilder.append(getLine(highLightLineMap, allLines, i));
            if (i < allLines.size() - 1 && i < fileLine.getEndLine()) {
                partContentBuilder.append("\n");
            }
        }
        return new MatchedFileContent(fileLine.getStartLine() + 1, partContentBuilder.toString());
    }

    /**
     * 获取所有正则匹配点
     *
     * @param allLines 文件内容的所有行
     * @param pattern  正则匹配编译表达式
     * @return 所有匹配正则表达式的字符串的位置
     */
    private static List<RegexMatchPoint> findAllRegexMatches(List<String> allLines, Pattern pattern) {
        // 先拿到所有的正则匹配点，行号从 0 开始
        List<RegexMatchPoint> regexMatchPoints = Lists.newArrayList();
        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            Matcher m = pattern.matcher(line);
            while (m.find()) {
                RegexMatchPoint regexMatchPoint = new RegexMatchPoint(i, m.start(), m.end());
                regexMatchPoints.add(regexMatchPoint);
            }
        }
        return regexMatchPoints;
    }

    /**
     * @param base64Content 文本内容（base64编码后的文本）
     * @param matchList     需要高亮的文本内容
     * @return 高亮内容段落集
     */
    public static List<MatchedFileContent> highlightBase64(String base64Content, List<String> matchList, int start) {
        if (StringUtils.isEmpty(base64Content) || CollectionUtils.isEmpty(matchList)) {
            return Collections.emptyList();
        }

        String content = Base64Utils.decodeContent(base64Content);
        return StreamUtils.map(matchList, match -> {
            String highlightContent = String.format("%s%s%s", CommonValues.HIGH_LIGHT_START, match, CommonValues.HIGH_LIGHT_END);
            String replaceContent = StringUtils.replace(content, match, highlightContent);
            MatchedFileContent matchedFileContent = new MatchedFileContent();
            matchedFileContent.setStartLine(start);
            matchedFileContent.setPartContent(replaceContent);
            return matchedFileContent;
        });
    }

    /**
     * 高亮文本内容
     */
    public static String highlightContent(String content, List<String> match) {
        if (CollectionUtils.isEmpty(match)) {
            return content;
        }

        try {
            for (String matchContent : match) {
                String highlightContent = String.format("%s%s%s", CommonValues.HIGH_LIGHT_START, matchContent, CommonValues.HIGH_LIGHT_END);
                String escapeRegex = RegexUtils.escapeRegexSpecialWord(matchContent);
                String escapeHighlight = RegexUtils.escapeRegexSpecialWord(highlightContent);
                content = content.replaceAll(escapeRegex, escapeHighlight);
            }
        } catch (Exception e) {
            LOG.error("highlight content error, content:{}, match:{}", content, match);
        }

        return content;
    }

    /**
     * 高亮一行的展示
     */
    private static String highlightOneLineContent(String content, List<RegexMatchPoint> points) {
        int start = 0;
        int lastMatchEnd = 0;
        StringBuilder sb = new StringBuilder();
        for (RegexMatchPoint point : points) {
            sb.append(content, start, point.getStart()).append(CommonValues.HIGH_LIGHT_START)
                    .append(content, point.getStart(), point.getEnd()).append(CommonValues.HIGH_LIGHT_END);
            start = point.getEnd();
            lastMatchEnd = point.getEnd();
        }
        sb.append(content.substring(lastMatchEnd));
        return sb.toString();
    }

    public static Integer dec(Integer pos) {
        if (pos != null && pos != 0) {
            return pos - 1;
        }
        return pos;
    }

    @Setter
    @Getter
    @ToString
    public static class RegexMatchPoint implements Comparable<RegexMatchPoint> {

        private Integer lineNo;

        private Integer start;

        private Integer end;

        public RegexMatchPoint(Integer lineNo, Integer start, Integer end) {
            this.lineNo = lineNo;
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
            return new RegexMatchPoint(lineNo, start, end);
        }

    }

    @Setter
    @Getter
    public static class MatchedFileLine {
        private Integer startLine;
        private Integer endLine;

        public MatchedFileLine(Integer startLine, Integer endLine) {
            this.startLine = startLine;
            this.endLine = endLine;
        }
    }

}
