package zzz.study.patterns.composite.whiterules;

import lombok.Getter;
import lombok.Setter;
import shared.utils.ReflectionUtil;

/**
 * @Description 单值不等于规则
 * @Date 2021/5/8 4:25 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class NotEqualsRule implements SingleWhiteRule {

    private Boolean single = Boolean.TRUE;
    private String field;
    private NotEqualCondition condition;

    public NotEqualsRule(String field, Object value) {
        this.field = field;
        this.condition = new NotEqualCondition(value);
    }

    public NotEqualsRule(String field, NotEqualCondition condition) {
        this.field = field;
        this.condition = condition;
    }

    @Override
    public boolean test(Object obj) {
        Object fv = ReflectionUtil.getValue(obj, field);
        return condition.test(fv);
    }

    @Getter
    @Setter
    class NotEqualCondition implements Condition {
        private Op op = Op.neq;
        private Object value;

        public NotEqualCondition(Object value) {
            this.value = value;
        }

        public boolean test(Object value) {
            return !this.value.equals(value);
        }
    }
}
