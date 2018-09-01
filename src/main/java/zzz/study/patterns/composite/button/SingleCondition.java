package zzz.study.patterns.composite.button;

import java.util.Map;

import lombok.Data;

@Data
public class SingleCondition implements ICondition {

  private BaseCondition cond;
  private Boolean result;

  public SingleCondition() {
  }

  public SingleCondition(String field, CondOp condOp, Object value, boolean result) {
    this.cond = new BaseCondition(field, condOp, value);
    this.result = result;
  }

  /**
   * 单条件测试
   * 这里仅做一个demo,实际需考虑健壮性和更多因素
   */
  @Override
  public boolean satisfiedBy(Map<String, Object> valueMap) {
    return this.cond.test(valueMap);
  }

}
