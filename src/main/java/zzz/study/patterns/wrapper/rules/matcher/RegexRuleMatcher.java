package zzz.study.patterns.wrapper.rules.matcher;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * "正则表达式"规则
 */
public class RegexRuleMatcher implements RuleMatcher<String> {

    /**
     * 正则表达式
     */
    private List<String> values;

    private List<Pattern> patterns;

    public RegexRuleMatcher(List<String> values) {
        this.values = values;
        this.patterns = this.values.stream().map(Pattern::compile).collect(Collectors.toList());

    }

    @Override
    public boolean match(String text) {
        if (text == null) {
            return false;
        }
        if (CollectionUtils.isEmpty(this.values)) {
            return false;
        }
        if (text == null) {
            return false;
        }
        for (Pattern pattern : patterns) {
            if (pattern.matcher(text).matches()) {
                return true;
            }
        }
        return false;
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }
}
