package zzz.study.patterns.wrapper.rules.condition;

import zzz.study.patterns.wrapper.rules.constants.Op;
import zzz.study.patterns.wrapper.rules.matcher.RegexRuleMatcher;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * "正则表达式"规则
 */
@Getter
public class RegexRuleCondition extends DefaultRuleCondition {

    public RegexRuleCondition(String field, List<String> values) {
        super(field, Op.REGEX, new RegexRuleMatcher(values));
    }

    public RegexRuleCondition(String field, String value) {
        super(field, Op.REGEX, new RegexRuleMatcher(Arrays.asList(value)));
    }
}
