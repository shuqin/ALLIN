package zzz.study.patterns.composite.escondition;

import java.util.List;

/**
 * Created by shuqin on 18/2/11.
 */
public class ConditionFactory {

    public static Condition eq(String fieldName, Object value) {
        return new EsCondition(fieldName, Op.eq, value);
    }

    public static Condition neq(String fieldName, Object value) {
        return new EsCondition(fieldName, Op.neq, value);
    }

    public static Condition in(String fieldName, List value) {
        return new EsCondition(fieldName, Op.in, value);
    }

    public static Condition range(String fieldName, Range range) {
        return new EsCondition(fieldName, Op.range, range);
    }

    public static Condition match(String fieldName, Match match) {
        return new EsCondition(fieldName, Op.match, match);
    }

}
