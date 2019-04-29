package zzz.study.patterns.composite.expression.parser;


import zzz.study.patterns.composite.expression.Expression;

/**
 * 解析简易格式格式的表达式
 *
 * 条件与结果用 => 分开； 每个表达式之间用 ； 区分。
 *
 * SingleExpression: 单条件的一个表达式
 * state = 5 => 待发货
 *
 * CombinedExpression: 多条件的一个表达式
 * activity_type = 13, state = 5, tcExtra = null => 待开奖
 *
 * WholeExpression: 多个表达式的集合
 *
 * state = 5 => 待发货 ; activity = 13, state = 50 => 待开奖
 *
 *
 */
public class ExpressionSimpleParser implements ExpressionParser {
  @Override
  public Expression parseSingle(String configJson) {
    return null;
  }

  @Override
  public Expression parseCombined(String configJson) {
    return null;
  }

  @Override
  public Expression parseWhole(String configJson) {
    return null;
  }
}
