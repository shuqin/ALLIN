package zzz.study.patterns.composite.whiterules;

import lombok.Getter;
import lombok.Setter;
import shared.utils.ReflectionUtil;

import java.util.List;

/**
 * @Description 包含匹配
 * @Date 2021/5/8 4:29 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class InRule implements WhiteRule {

    private Boolean single = Boolean.TRUE;
    private String field;
    private InCondition condition;

    public InRule(String field, List values) {
        this.field = field;
        this.condition = new InCondition(values);
    }

    public InRule(String field, InCondition condition) {
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
    class InCondition implements Condition {
        private Op op = Op.in;
        private List values;

        public InCondition(List values) {
            this.values = values;
        }

        public boolean test(Object value) {
            return this.values.contains(value);
        }
    }
}
