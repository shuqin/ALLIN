package zzz.study.patterns.composite.expression;

import java.util.Map;

public interface Expression {

  /**
   * 获取满足条件时要返回的值
   */
  String getResult(Map<String, Object> valueMap);

}
