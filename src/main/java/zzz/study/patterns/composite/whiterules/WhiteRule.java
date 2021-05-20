package zzz.study.patterns.composite.whiterules;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 白名单规则
 * @Date 2021/5/8 4:24 下午
 * @Created by qinshu
 */
public interface WhiteRule {

    String IS_SINGLE_RULE = "single";
    String WHITE_RULES_FIELD = "whiteRules";
    String CONDITION = "condition";
    String OP = "op";

    boolean test(Object obj);

    interface Condition {
        boolean test(Object value);
    }

    default String expr() {
        return JSON.toJSONString(this);
    }

    static WhiteRule parse(String ruleStr) {
        JSONObject jsonObject = JSON.parseObject(ruleStr);
        if (jsonObject.getBoolean(IS_SINGLE_RULE) == Boolean.TRUE) {
            return parseSingle(jsonObject);
        }
        JSONArray jsonArray = jsonObject.getJSONArray(WHITE_RULES_FIELD);
        Op op = Op.getOp(jsonObject.getString(OP));
        List<WhiteRule> whiteRuleList = new ArrayList<>();
        for (int i=0; i < jsonArray.size(); i++) {
            // 目前不考虑列表嵌套的情形
            whiteRuleList.add(parseSingle(jsonArray.getJSONObject(i)));
        }
        return op == Op.and ? new AndRule(whiteRuleList) : new OrRule(whiteRuleList);
    }

    static WhiteRule parseSingle(JSONObject jsonObject) {
        Op op = Op.getOp(jsonObject.getJSONObject(CONDITION).getString(OP));
        switch (op) {
            case eq:
                return jsonObject.toJavaObject(EqualsRule.class);
            case neq:
                return jsonObject.toJavaObject(NotEqualsRule.class);
            case in:
                return jsonObject.toJavaObject(InRule.class);
            case range:
                return jsonObject.toJavaObject(RangeRule.class);
            case match:
                return jsonObject.toJavaObject(MatchRule.class);
            default:
                return null;
        }
    }
}