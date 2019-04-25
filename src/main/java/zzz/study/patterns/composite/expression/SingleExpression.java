package zzz.study.patterns.composite.expression;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Map;

@Data
public class SingleExpression implements Expression {

  private BaseCondition cond;
  protected String result;

  public SingleExpression() {}

  public SingleExpression(BaseCondition cond, String result) {
    this.cond = cond;
    this.result = result;
  }

  public static SingleExpression getInstance(String configJson) {
    return JSON.parseObject(configJson, SingleExpression.class);
  }

  @Override
  public String getResult(Map<String, Object> valueMap) {
    return cond.satisfiedBy(valueMap) ? result : "";
  }
}
