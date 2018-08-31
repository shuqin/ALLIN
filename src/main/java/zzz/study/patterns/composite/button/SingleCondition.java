package zzz.study.patterns.composite.button;

import com.alibaba.fastjson.JSON;

import java.util.Map;

import lombok.Data;

@Data
public class SingleCondition extends BaseCondition implements ICondition {

  private Boolean result;

  public SingleCondition() {
    super();
    this.result = false;
  }

  public SingleCondition(String field, CondOp condOp, Object value, boolean result) {
    super(field, condOp, value);
    this.result = result;
  }

  public static SingleCondition getInstance(String configJson) {
    return JSON.parseObject(configJson, SingleCondition.class);
  }

  /**
   * 单条件测试
   * 这里仅做一个demo,实际需考虑健壮性和更多因素
   */
  @Override
  public boolean satisfiedBy(Map<String, Object> valueMap) {
    return this.test(valueMap);
  }

}
