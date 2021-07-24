package zzz.study.patterns.wrapper.rules.condition;

/**
 * 白名单规则匹配器
 */
public interface RuleCondition<T> {

    /**
     * 匹配指定对象 T
     * @param obj 待匹配对象 T
     * @return 若匹配，返回 true ，否则 返回 false
     */
    boolean match(T obj);
}
