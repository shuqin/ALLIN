package zzz.study.patterns.composite.expression.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import zzz.study.patterns.composite.expression.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 解析 JSON 格式的表达式
 *
 * SingleExpression: 单条件的一个表达式
 * {"cond": {"field": "state", "op":"eq", "value":5}, "result":"待发货"}
 *
 * CombinedExpression: 多条件的一个表达式
 * {"conditions": [{"field": "activity_type", "op":"eq", "value":13},{"field": "state", "op":"eq", "value":5}, {"field": "tcExtra", "op":"isnull"}], "result":"待开奖"}
 *
 * WholeExpression: 多个表达式的集合
 * '''
 *   [{"cond": {"field": "state", "op":"eq", "value":5}, "result":"待发货"},
 *    {"conditions": [{"field": "activity_type", "op":"eq", "value":13},{"field": "state", "op":"eq", "value":50}], "result":"待开奖"}]
 * '''
 *
 */
public class ExrepssionJsonParser implements ExpressionParser {

  @Override
  public Expression parseSingle(String configJson) {
    return JSON.parseObject(configJson, SingleExpression.class);
  }

  @Override
  public Expression parseCombined(String configJson) {
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

  @Override
  public Expression parseWhole(String configJson) {
    JSONArray jsonArray = JSON.parseArray(configJson);
    List<Expression> expressions = new ArrayList<>();
    if (jsonArray != null && jsonArray.size() > 0) {
      expressions = jsonArray.stream().map(cond -> convertFrom((JSONObject)cond)).collect(Collectors.toList());
    }
    return new WholeExpressions(expressions);
  }

  private static Expression convertFrom(JSONObject expressionObj) {
    if (expressionObj.containsKey("cond")) {
      return JSONObject.toJavaObject(expressionObj, SingleExpression.class);
    }
    if (expressionObj.containsKey("conditions")) {
      return CombinedExpression.getInstance(expressionObj.toJSONString());
    }
    return null;
  }
}
