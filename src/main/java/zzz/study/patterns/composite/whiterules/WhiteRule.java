package zzz.study.patterns.composite.whiterules;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import shared.utils.JsonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 白名单规则
 * @Date 2021/5/8 4:24 下午
 * @Created by qinshu
 */
public interface WhiteRule extends Serializable {

    String IS_SINGLE_RULE = "single";
    String WHITE_RULES_FIELD = "whiteRules";
    String CONDITION = "condition";
    String OP = "op";

    static WhiteRule parse(String ruleStr) {
        JSONObject jsonObject = JSON.parseObject(ruleStr);
        if (jsonObject.getBoolean(IS_SINGLE_RULE) == Boolean.TRUE) {
            return parseSingle(jsonObject);
        }
        JSONArray jsonArray = jsonObject.getJSONArray(WHITE_RULES_FIELD);
        Op op = Op.getOp(jsonObject.getString(OP));
        List<WhiteRule> whiteRuleList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            // 目前不考虑列表嵌套的情形
            whiteRuleList.add(parseSingle(jsonArray.getJSONObject(i)));
        }
        return op == Op.and ? new AndRule(whiteRuleList) : new OrRule(whiteRuleList);
    }

    static WhiteRule parseSingle(JSONObject jsonObject) {
        Op op = Op.getOp(jsonObject.getJSONObject(CONDITION).getString(OP));
        switch (op) {
            case eq:
                return JsonUtils.toObject(jsonObject.toJSONString(), EqualsRule.class);
            case neq:
                return JsonUtils.toObject(jsonObject.toJSONString(), NotEqualsRule.class);
            case in:
                return JsonUtils.toObject(jsonObject.toJSONString(), InRule.class);
            case range:
                return JsonUtils.toObject(jsonObject.toJSONString(), RangeRule.class);
            case match:
                return JsonUtils.toObject(jsonObject.toJSONString(), MatchRule.class);
            default:
                return null;
        }
    }

    boolean test(Object obj);

    default String expr() {
        return JSON.toJSONString(this);
    }

    interface Condition {
        boolean test(Object value);
    }
}
