package zzz.study.patterns.wrapper.rules.matcher;

/**
 * 规则匹配对象
 *
 * 可以匹配指定元素 E
 *
 * NOTE:
 * RuleMatcher 是基础匹配器
 * RuleCondition 是 RuleMatcher 的组合与封装，对应于业务对象的匹配
 *
 */
public interface RuleMatcher<E> {

    /**
     * 匹配指定元素 e
     * @param e 待匹配的对象
     * @return 如果匹配，返回 true ，否则返回 false
     */
    boolean match(E e);
}
