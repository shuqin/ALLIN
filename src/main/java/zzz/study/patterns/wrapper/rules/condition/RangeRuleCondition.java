package zzz.study.patterns.wrapper.rules.condition;

import zzz.study.patterns.wrapper.rules.constants.Op;
import zzz.study.patterns.wrapper.rules.matcher.RangeRuleMatcher;
import lombok.Getter;

/**
 * "范围"规则
 */
@Getter
public class RangeRuleCondition extends DefaultRuleCondition {

    public RangeRuleCondition(String field, Comparable start, Comparable end) {
        super(field, Op.RANGE, new RangeRuleMatcher(start, end));
    }

}
 