package zzz.study.patterns.composite.escondition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;

/**
 * Created by shuqin on 18/2/8.
 */
@Data
public class CompositeShouldCondition implements Condition, Serializable {

  private static final long serialVersionUID = -3706269911758312468L;

  private List<Condition> conditions;

  private Integer shouldMatchMinimum = 1;

  public CompositeShouldCondition() {
    this.conditions = Lists.newArrayList();
    this.shouldMatchMinimum = 1;
  }

  public CompositeShouldCondition(List<Condition> conditions, Integer shouldMinimumMatch) {
    this.conditions = conditions;
    this.shouldMatchMinimum = shouldMinimumMatch;
  }

  @Override
  public Condition and(Condition c) {
    return new CompositeMustCondition(Lists.newArrayList(c, this));
  }

  @Override
  public Condition or(Condition c, Integer shouldMinimumMatch) {
    return new CompositeShouldCondition(Lists.newArrayList(c, this),
                                        shouldMinimumMatch);
  }

  @Override
  public Map expr() {
    List<Map> conditions = this.conditions.stream().map(Condition::expr).collect(
        Collectors.toList());
    return ImmutableMap.of("bool", ImmutableMap.of("should", conditions, "minimum_should_match", shouldMatchMinimum));
  }
}
