package zzz.study.patterns.composite.expression;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CombinedCondition implements Condition {

  private List<BaseCondition> conditions;

  public CombinedCondition() {
    this.conditions = new ArrayList<>();
  }

  public CombinedCondition(List<BaseCondition> conditions) {
    this.conditions = conditions;
  }

  @Override
  public boolean satisfiedBy(Map<String, Object> valueMap) {
    if (CollectionUtils.isEmpty(conditions)) {
      return true;
    }
    for (BaseCondition condition: conditions) {
      if (!condition.satisfiedBy(valueMap)) {
        return false;
      }
    }
    return true;
  }

  public static CombinedCondition getInstance(String configJson) {
    List<BaseCondition> conditions = JSON.parseArray(configJson, BaseCondition.class);
    return new CombinedCondition(conditions);
  }
}
