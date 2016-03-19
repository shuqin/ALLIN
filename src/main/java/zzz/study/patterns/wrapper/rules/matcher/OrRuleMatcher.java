package zzz.study.patterns.wrapper.rules.matcher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * "或"规则
 */
@Getter
@Setter
@ToString
public class OrRuleMatcher implements RuleMatcher {

    private List<RuleMatcher> ruleMatchers = new ArrayList<>(); //条件模板

    public OrRuleMatcher(List<RuleMatcher> ruleMatchers) {
        this.ruleMatchers = ruleMatchers;
    }

    @Override
    public boolean match(Object value) {
        for (RuleMatcher ruleMatcher : ruleMatchers) {
            if (ruleMatcher.match(value)) {
                return true;
            }
        }
        return false;
    }

}