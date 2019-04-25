package zzz.study.patterns.composite.expression;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CombinedExpression implements Expression {

  private CombinedCondition conditions;
  private String result;

  public CombinedExpression() {}

  public CombinedExpression(CombinedCondition conditions, String result) {
    this.conditions = conditions;
    this.result = result;
  }

  @Override
  public String getResult(Map<String, Object> valueMap) {
    return conditions.satisfiedBy(valueMap) ? result : "";
  }

  public static CombinedExpression getInstance(String configJson) {
    try {
      JSONObject jsonObject = JSON.parseObject(configJson);
      String result = jsonObject.getString("result");
      JSONArray condArray = jsonObject.getJSONArray("conditions");
      List<BaseCondition> conditionList = new ArrayList<>();

      if (condArray != null || condArray.size() >0) {
        conditionList = condArray.stream().map(cond -> JSONObject.toJavaObject((JSONObject)cond, BaseCondition.class)).collect(Collectors.toList());
      }
      CombinedCondition combinedCondition = new CombinedCondition(conditionList);
      return new CombinedExpression(combinedCondition, result);
    } catch (Exception ex) {
      return null;
    }
  }
}
