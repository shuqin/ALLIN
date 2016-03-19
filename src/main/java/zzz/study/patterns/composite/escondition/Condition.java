package zzz.study.patterns.composite.escondition;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Created by shuqin on 18/2/8.
 */
public interface Condition {

    Condition and(Condition c);

    Map expr();    // ES 查询对象

    default String json() {
        return JSON.toJSONString(this);
    }

    default Condition or(Condition c, Integer shouldMinimumMatch) {
        List<Condition> shouldConditions = Lists.newArrayList(c, this);
        return new CompositeShouldCondition(shouldConditions, shouldMinimumMatch);
    }

    default Condition or(List<Condition> conds, Integer shouldMinimumMatch) {
        List<Condition> shouldConditions = Lists.newArrayList(this);
        shouldConditions.addAll(conds);
        return new CompositeShouldCondition(shouldConditions, shouldMinimumMatch);
    }

}
