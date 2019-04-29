package zzz.study.patterns.composite.expression.parser;


import zzz.study.patterns.composite.expression.Expression;

public interface ExpressionParser {
  Expression parseSingle(String configJson);
  Expression parseCombined(String configJson);
  Expression parseWhole(String configJson);
}
