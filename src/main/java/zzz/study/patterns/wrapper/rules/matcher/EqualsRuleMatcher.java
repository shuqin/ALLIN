package zzz.study.patterns.wrapper.rules.matcher;

import lombok.Getter;

/**
 * "等于"规则
 */
@Getter
public class EqualsRuleMatcher<E> implements RuleMatcher<E> {

    private E value;

    public EqualsRuleMatcher(E value) {
        this.value = value;
    }

    @Override
    public boolean match(E e) {
        if (e == null) {
            return false;
        }
        if (value == null) {
            return false;
        }
        return this.value.equals(e);
    }

}