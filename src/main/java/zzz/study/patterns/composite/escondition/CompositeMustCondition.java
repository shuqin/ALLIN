package zzz.study.patterns.composite.escondition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shuqin on 18/2/8.
 */
@Data
public class CompositeMustCondition implements Condition, Serializable {

    private static final long serialVersionUID = 2546838275170403153L;

    private List<Condition> multiConditions;

    public CompositeMustCondition() {
        multiConditions = Lists.newArrayList();
    }

    public CompositeMustCondition(List<Condition> multiConditions) {
        this.multiConditions = multiConditions;
    }

    @Override
    public Condition and(Condition c) {
        multiConditions.add(c);
        return new CompositeMustCondition(multiConditions);
    }

    @Override
    public Condition or(Condition c, Integer shouldMinimumMatch) {
        List<Condition> shouldConditions = Lists.newArrayList(c, this);
        return new CompositeShouldCondition(shouldConditions, shouldMinimumMatch);
    }

    @Override
    public Map expr() {
        List<Map> conditions = multiConditions.stream().map(Condition::expr).collect(Collectors.toList());
        return ImmutableMap.of("bool", ImmutableMap.of("must", conditions));
    }
}
