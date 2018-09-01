package zzz.study.patterns.composite.button.strategy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zzz.study.patterns.composite.button.ButtonCondition;
import zzz.study.patterns.composite.button.ICondition;
import zzz.study.patterns.composite.button.MultiCondition;
import zzz.study.patterns.composite.button.SingleCondition;

public class JSONStrategy implements ConditionParserStrategy {

  @Override
  public SingleCondition parseSingle(String condJson) {
    return JSON.parseObject(condJson, SingleCondition.class);
  }

  @Override
  public MultiCondition parseMulti(String condJson) {
    return JSON.parseObject(condJson, MultiCondition.class);
  }

  @Override
  public ButtonCondition parse(String condJson) {
    Map<String, Object> configMap = JSON.parseObject(condJson);
    Boolean result = ((JSONObject) configMap).getBoolean("defaultResult");
    JSONArray conditions = ((JSONObject) configMap).getJSONArray("buttonRules");
    List<ICondition> allConditions = new ArrayList<>();
    for (int i=0; i < conditions.size(); i++) {
      Map condition = (Map) conditions.get(i);
      if (condition.containsKey("cond")) {
        allConditions.add(JSONObject.parseObject(condition.toString(), SingleCondition.class));
      }
      else if (condition.containsKey("conditions")){
        allConditions.add(JSONObject.parseObject(condition.toString(), MultiCondition.class));
      }
    }
    return new ButtonCondition(allConditions, result);
  }
}
