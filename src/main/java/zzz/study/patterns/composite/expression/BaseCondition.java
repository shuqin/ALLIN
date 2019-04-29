package zzz.study.patterns.composite.expression;

import lombok.Data;
import zzz.study.utils.MapUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Data
public class BaseCondition implements Condition {

  private String field;
  private Op op;
  private Object value;

  public BaseCondition() {}

  public BaseCondition(String field, Op op, Object value) {
    this.field = field;
    this.op = op;
    this.value = value;
  }

  public boolean satisfiedBy(Map<String, Object> valueMap) {
    try {
      if (valueMap == null || valueMap.size() == 0) {
        return false;
      }
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
            return Objects.equals(MapUtil.readVal(valueMap, field), value);
        default:
          return false;
      }
    } catch (Exception ex) {
      return false;
    }
  }
}
