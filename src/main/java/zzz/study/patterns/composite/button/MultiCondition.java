package zzz.study.patterns.composite.button;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class MultiCondition implements ICondition {

  private List<BaseCondition> conditions;
  private Boolean result;

  public MultiCondition() {
    this.conditions = new ArrayList<>();
    this.result = false;
  }

  public MultiCondition(List<BaseCondition> conditions, Boolean result) {
    this.conditions = conditions;
    this.result = result;
  }

  public static MultiCondition getInstance(String configJson) {
    return JSON.parseObject(configJson, MultiCondition.class);
  }

  @Override
  public boolean satisfiedBy(Map<String, Object> valueMap) {
    for (BaseCondition bc: conditions) {
      if (!bc.test(valueMap)) {
        return false;
      }
    }
    return true;
  }
}
