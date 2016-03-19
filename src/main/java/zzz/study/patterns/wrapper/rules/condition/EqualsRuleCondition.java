package zzz.study.patterns.wrapper.rules.condition;

import zzz.study.patterns.wrapper.rules.constants.Op;
import zzz.study.patterns.wrapper.rules.matcher.EqualsRuleMatcher;

/**
 * "等于"规则
 */
public class EqualsRuleCondition extends DefaultRuleCondition {

    public EqualsRuleCondition(String field, Object value) {
        super(field, Op.EQ, new EqualsRuleMatcher<>(value));
    }
}