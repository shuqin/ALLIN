package zzz.study.patterns.composite.expression;

import lombok.Data;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Data
public class BaseCondition implements Condition {

  private String field;
  private Op op;
  private Object value;

  private String key;

  public BaseCondition() {}

  public BaseCondition(String field, Op op, Object value) {
    this.field = field;
    this.op = op;
    this.value = value;
  }

  public BaseCondition(String field, Op op, Object value, String key) {
    this(field, op, value);
    this.key = key;
  }

  public boolean satisfiedBy(Map<String, Object> valueMap) {
    try {
      Object passedValue = valueMap.get(field);
      switch (this.getOp()) {
        case isnull:
          return passedValue == null;
        case notnull:
          return passedValue != null;
        case eq:
          return Objects.equals(value, passedValue);
        case neq:
          return !Objects.equals(value, passedValue);
        case in:
          if (value == null || !(value instanceof Collection)) {
            return false;
          }
          return ((Collection)value).contains(passedValue);
        case contains:
          if (passedValue == null || !(passedValue instanceof Map)) {
            return false;
          }
          return ((Map)passedValue).containsKey(value);
        case notcontains:
          if (passedValue == null || !(passedValue instanceof Map)) {
            return true;
          }
          return !((Map)passedValue).containsKey(value);
          case get:
            if (passedValue == null || !(passedValue instanceof Map)) {
              return false;
            }
            return Objects.equals(((Map)passedValue).get(key), value);
        default:
          return false;
      }
    } catch (Exception ex) {
      return false;
    }
  }
}
