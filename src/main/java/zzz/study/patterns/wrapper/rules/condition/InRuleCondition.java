package zzz.study.patterns.wrapper.rules.condition;

import zzz.study.patterns.wrapper.rules.constants.Op;
import zzz.study.patterns.wrapper.rules.matcher.InRuleMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * "包含"规则
 */
public class InRuleCondition extends DefaultRuleCondition {

    private InRuleMatcher inRuleMatcher;

    public InRuleCondition(String field, List<Object> values) {
        super(field, Op.IN, new InRuleMatcher(values));
    }

    public InRuleCondition(String field, Object value) {
        super(field, Op.IN, new InRuleMatcher(Arrays.asList(value)));
    }
}
 