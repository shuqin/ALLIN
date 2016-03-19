package zzz.study.patterns.composite.button;

import lombok.Data;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Data
public class BaseCondition {

    protected String field;
    protected CondOp op;
    protected Object value;

    public BaseCondition() {
    }

    public BaseCondition(String field, CondOp op, Object value) {
        this.field = field;
        this.op = op;
        this.value = value;
    }

    public boolean test(Map<String, Object> valueMap) {
        try {
            Object passedValue = valueMap.get(field);
            switch (this.getOp()) {
                case eq:
                    return Objects.equals(value, passedValue);
                case neq:
                    return !Objects.equals(value, passedValue);
                case lt:
                    // 需要根据格式转换成相应的对象然后 compareTo
                    return ((Comparable) passedValue).compareTo(value) < 0;
                case gt:
                    return ((Comparable) passedValue).compareTo(value) > 0;
                case lte:
                    return ((Comparable) passedValue).compareTo(value) <= 0;
                case gte:
                    return ((Comparable) passedValue).compareTo(value) >= 0;
                case in:
                    return ((Collection) value).contains(passedValue);
                default:
                    return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
