package zzz.study.patterns.composite.whiterules;

import lombok.Getter;
import lombok.Setter;
import shared.utils.ReflectionUtil;

/**
 * @Description 单值等于规则
 * @Date 2021/5/8 4:25 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class EqualsRule implements SingleWhiteRule {

    private Boolean single = Boolean.TRUE;
    private String field;
    private EqualCondition condition;

    public EqualsRule() {
    }

    public EqualsRule(String field, Object value) {
        this.field = field;
        this.condition = new EqualCondition(value);
    }

    public EqualsRule(String field, EqualCondition condition) {
        this.field = field;
        this.condition = condition;
    }

    @Override
    public boolean test(Object obj) {
        Object fv = ReflectionUtil.getValue(obj, field);
        return this.condition.test(fv);
    }

    @Getter
    @Setter
    class EqualCondition implements Condition {
        private Op op = Op.eq;
        private Object value;

        public EqualCondition(Object value) {
            this.value = value;
        }

        public boolean test(Object value) {
            return this.value.equals(value);
        }

    }
}
