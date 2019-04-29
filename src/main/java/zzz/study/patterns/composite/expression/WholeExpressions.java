package zzz.study.patterns.composite.expression;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class WholeExpressions implements Expression {

  private List<Expression> expressions;

  public WholeExpressions() {
    this.expressions = new ArrayList<>();
  }

  public WholeExpressions(List<Expression> expressions) {
    this.expressions = expressions;
  }

  public void addExpression(Expression expression) {
    this.expressions.add(expression);
  }

  public void addExpressions(List<Expression> expression) {
    this.expressions.addAll(expression);
  }

  public String getResult(Map<String,Object> valueMap) {
    for (Expression expression: expressions) {
      String result = expression.getResult(valueMap);
      if (StringUtils.isNotBlank(result)) {
        return result;
      }
    }
    return "";
  }

  public static WholeExpressions getInstance(String configJson) {
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
