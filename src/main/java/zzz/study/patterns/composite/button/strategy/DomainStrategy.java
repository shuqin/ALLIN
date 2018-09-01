package zzz.study.patterns.composite.button.strategy;

import zzz.study.patterns.composite.button.ButtonCondition;
import zzz.study.patterns.composite.button.MultiCondition;
import zzz.study.patterns.composite.button.SingleCondition;

public class DomainStrategy implements ConditionParserStrategy {

  @Override
  public SingleCondition parseSingle(String domainStr) {
    return null;
  }

  @Override
  public MultiCondition parseMulti(String domainStr) {
    return null;
  }

  @Override
  public ButtonCondition parse(String domainStr) {
    return null;
  }
}
