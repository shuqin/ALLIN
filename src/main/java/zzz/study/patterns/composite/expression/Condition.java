package zzz.study.patterns.composite.expression;

import java.util.Map;

public interface Condition {

  /**
   * 传入的 valueMap 是否满足条件对象
   * @param valueMap 值对象
   * 若 valueMap 满足条件对象，返回 true , 否则返回 false .
   */
  boolean satisfiedBy(Map<String, Object> valueMap);
}
