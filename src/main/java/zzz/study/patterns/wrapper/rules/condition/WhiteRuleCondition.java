package zzz.study.patterns.wrapper.rules.condition;

import java.util.List;

/**
 * 白名单规则条件
 *
 * 针对某个事件属性 e 的白名单规则 ruleMatcher 的匹配
 */
public class WhiteRuleCondition<E> {

    private E element;
    private List<RuleCondition> ruleConditions;

    public WhiteRuleCondition(List<RuleCondition> ruleConditions) {
        this.ruleConditions = ruleConditions;
    }

    public WhiteRuleCondition(E element, List<RuleCondition> ruleConditions) {
        this.element = element;
        this.ruleConditions = ruleConditions;
    }

    /**
     * 测试外部传入的入侵事件属性
     */
    public boolean match(E element) {
        return ruleConditions.stream().anyMatch(ruleCondition -> ruleCondition.match(element));
    }

    /**
     * 如果本对象包含了入侵事件属性，则测试内部事件属性
     */
    public boolean match() {
        return match(element);
    }
}
