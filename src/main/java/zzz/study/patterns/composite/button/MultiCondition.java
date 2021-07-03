package zzz.study.patterns.composite.button;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public boolean satisfiedBy(Map<String, Object> valueMap) {
        for (BaseCondition bc : conditions) {
            if (!bc.test(valueMap)) {
                return false;
            }
        }
        return true;
    }
}
