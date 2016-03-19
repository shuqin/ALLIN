package zzz.study.patterns.composite.whiterules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @Description 原子规则
 * @Date 2021/5/18 6:56 下午
 * @Created by qinshu
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RangeRule.class, name = "range")
})
public interface SingleWhiteRule extends WhiteRule {

    String getField();

    Condition getCondition();
}
