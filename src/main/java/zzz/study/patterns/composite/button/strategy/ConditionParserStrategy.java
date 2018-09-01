package zzz.study.patterns.composite.button.strategy;

import zzz.study.patterns.composite.button.ButtonCondition;
import zzz.study.patterns.composite.button.MultiCondition;
import zzz.study.patterns.composite.button.SingleCondition;

public interface ConditionParserStrategy {

  SingleCondition parseSingle(String express);
  MultiCondition parseMulti(String express);
  ButtonCondition parse(String express);
}
