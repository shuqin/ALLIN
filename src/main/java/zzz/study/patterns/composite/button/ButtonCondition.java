package zzz.study.patterns.composite.button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ButtonCondition {

    private List<ICondition> buttonRules;

    private Boolean defaultResult;

    public ButtonCondition() {
        this.buttonRules = new ArrayList<>();
        this.defaultResult = false;
    }

    public ButtonCondition(List<ICondition> matches, Boolean defaultResult) {
        this.buttonRules = matches;
        this.defaultResult = defaultResult;
    }

    public static ButtonCondition getInstance(String configJson) {
        Map<String, Object> configMap = JSON.parseObject(configJson);
        Boolean result = ((JSONObject) configMap).getBoolean("defaultResult");
        JSONArray conditions = ((JSONObject) configMap).getJSONArray("buttonRules");
        List<ICondition> allConditions = new ArrayList<>();
        for (int i = 0; i < conditions.size(); i++) {
            Map condition = (Map) conditions.get(i);
            if (condition.containsKey("cond")) {
                allConditions.add(JSONObject.parseObject(condition.toString(), SingleCondition.class));
            } else if (condition.containsKey("conditions")) {
                allConditions.add(JSONObject.parseObject(condition.toString(), MultiCondition.class));
            }
        }
        return new ButtonCondition(allConditions, result);
    }

    public boolean satisfiedBy(Map<String, Object> valueMap) {
        // 这里是一个责任链模式，为简单起见，采用了列表遍历
        for (ICondition cond : buttonRules) {
            if (cond.satisfiedBy(valueMap)) {
                return cond.getResult();
            }
        }
        return defaultResult;
    }
}
