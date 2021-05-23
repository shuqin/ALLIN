package zzz.study.patterns.composite.whiterules;

import lombok.Getter;
import lombok.Setter;
import shared.utils.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description 正则匹配
 * @Date 2021/5/8 4:36 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class MatchRule implements SingleWhiteRule {

    private Boolean single = Boolean.TRUE;
    private String field;
    private MatchCondition condition;

    public MatchRule() {}

    public MatchRule(String field, List<String> values) {
        this.field = field;
        this.condition = new MatchCondition(values);

    }

    public MatchRule(String field, MatchCondition condition) {
        this.field = field;
        this.condition = condition;

    }

    @Override
    public boolean test(Object obj) {
        Object fv = ReflectionUtil.getValue(obj, field);
        return condition.test(fv);
    }

    @Getter
    @Setter
    class MatchCondition implements Condition {
        private Op op = Op.match;
        private List<String> values;

        private List<Pattern> patterns = new ArrayList<>();

        public MatchCondition(List<String> values) {
            this.values = values;
            this.patterns = values.stream().map(Pattern::compile).collect(Collectors.toList());
        }

        public boolean test(Object fv) {
            for (Pattern p: patterns) {
                if (p.matcher((String)fv).matches()) {
                    return true;
                }
            }
            return false;
        }
    }
}
