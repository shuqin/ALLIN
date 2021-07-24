package zzz.study.patterns.wrapper.rules.matcher;

import lombok.Getter;

/**
 * "范围包含"规则
 */
@Getter
public class RangeRuleMatcher implements RuleMatcher<Comparable> {

    private Comparable start;
    private Comparable end;

    public RangeRuleMatcher(Comparable start, Comparable end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean match(Comparable obj) {
        if (obj == null) {
            return false;
        }
        return obj.compareTo(start) > 0 && obj.compareTo(end) < 0;
    }
}
 