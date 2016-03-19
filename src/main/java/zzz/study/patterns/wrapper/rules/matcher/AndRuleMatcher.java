package zzz.study.patterns.wrapper.rules.matcher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * "与"规则
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AndRuleMatcher implements RuleMatcher {

    private List<RuleMatcher> ruleMatchers = new ArrayList<>(); //运算符规则条件

    public AndRuleMatcher(List<RuleMatcher> ruleMatchers) {
        this.ruleMatchers = ruleMatchers;
    }

    @Override
    public boolean match(Object value) {
        for (RuleMatcher ruleMatcher : ruleMatchers) {
            if (!ruleMatcher.match(value)) {
                return false;
            }
        }
        return true;
    }

}
 
 