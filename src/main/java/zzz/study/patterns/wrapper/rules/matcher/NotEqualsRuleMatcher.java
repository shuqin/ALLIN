package zzz.study.patterns.wrapper.rules.matcher;

import lombok.Getter;

/**
 * "不等于"规则
 */
@Getter
public class NotEqualsRuleMatcher<E> implements RuleMatcher<E> {

    private E value;

    public NotEqualsRuleMatcher(E value) {
        this.value = value;
    }

    @Override
    public boolean match(E obj) {
        if (obj == null) {
            return false;
        }
        if (value == null) {
            return false;
        }
        return !this.value.equals(obj);
    }
}
