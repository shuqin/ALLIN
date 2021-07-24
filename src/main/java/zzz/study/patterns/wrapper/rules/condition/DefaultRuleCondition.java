package zzz.study.patterns.wrapper.rules.condition;

import zzz.study.patterns.wrapper.rules.constants.Op;
import zzz.study.patterns.wrapper.rules.matcher.RuleMatcher;

/**
 * 默认的规则实现
 *
 * Created by qinshu on 2021/7/24
 */
public class DefaultRuleCondition extends AbstractRuleCondition {

    public DefaultRuleCondition(String field, Op op) {
        super(field, op);
    }

    public DefaultRuleCondition(String field, Op op, RuleMatcher ruleMatcher) {
        super(field, op, ruleMatcher);
    }

    @Override
    public boolean check(Object obj) {
        if (ruleMatcher == null) {
            return false;
        }
        return ruleMatcher.match(obj);
    }
}
