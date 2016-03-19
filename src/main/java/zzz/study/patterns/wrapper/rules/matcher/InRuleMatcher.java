package zzz.study.patterns.wrapper.rules.matcher;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * "集合包含"规则
 */
@Getter
public class InRuleMatcher<E> implements RuleMatcher<E> {

    private List<E> values;

    public InRuleMatcher(List<E> values) {
        this.values = values;
    }

    @Override
    public boolean match(E obj) {
        if (obj == null) {
            return false;
        }
        if (CollectionUtils.isEmpty(values)) {
            return false;
        }
        return values.contains(obj);
    }

    public List<E> getValues() {
        return Collections.unmodifiableList(values);
    }
}
 