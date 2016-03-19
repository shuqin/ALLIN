package zzz.study.patterns.composite.whiterules;

import lombok.Getter;
import lombok.Setter;
import shared.utils.ReflectionUtil;

/**
 * @Description 范围匹配
 * @Date 2021/5/8 4:30 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class RangeRule implements SingleWhiteRule {

    private Boolean single = Boolean.TRUE;
    private String field;
    private RangeCondition condition;

    public RangeRule() {
    }

    public RangeRule(String field, Comparable start, Comparable end) {
        this.field = field;
        this.condition = new RangeCondition(start, end);
    }

    public RangeRule(String field, RangeCondition condition) {
        this.field = field;
        this.condition = condition;
    }

    @Override
    public boolean test(Object obj) {
        Object fv = ReflectionUtil.getValue(obj, field);
        Comparable cv = (Comparable) fv;
        return condition.test(cv);
    }

    @Getter
    @Setter
    class RangeCondition implements Condition {
        private Op op = Op.range;
        private Comparable start;
        private Comparable end;

        public RangeCondition(Comparable start, Comparable end) {
            this.start = start;
            this.end = end;
        }

        public boolean test(Object value) {
            return start.compareTo(value) < 0 && end.compareTo(value) >= 0;
        }
    }
}
