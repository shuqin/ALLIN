package zzz.study.patterns.composite.whiterules;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 与逻辑
 * @Date 2021/5/8 4:41 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class AndRule implements WhiteRule {

    private Boolean single = Boolean.FALSE;
    private List<WhiteRule> whiteRules = new ArrayList<>();
    private Op op = Op.and;

    public AndRule() {}

    public AndRule(List<WhiteRule> whiteRules) {
        this.whiteRules = whiteRules;
    }

    public AndRule(WhiteRule... whiteRules) {
        this.whiteRules = Arrays.asList(whiteRules);
    }


    @Override
    public boolean test(Object value) {
        for (WhiteRule rule: whiteRules) {
            if (!rule.test(value)) {
                return false;
            }
        }
        return true;
    }
}
