package zzz.study.patterns.wrapper.rules.condition;

import zzz.study.patterns.wrapper.rules.constants.Op;
import zzz.study.patterns.wrapper.rules.matcher.NotEqualsRuleMatcher;

/**
 * "不等于"规则
 */
public class NotEqualsRuleCondition extends DefaultRuleCondition {

    public NotEqualsRuleCondition(String field, Object value) {
        super(field, Op.NEQ, new NotEqualsRuleMatcher(value));
    }
}
