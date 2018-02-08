package zzz.study.patterns.composite.escondition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Created by shuqin on 18/2/8.
 */
@Data
public class EsCondition implements Condition, Serializable {

  private static final long serialVersionUID = -209082552315760372L;

  /** ES 字段名称 */
  private String fieldName;

  /** 匹配符 */
  private Op op;

  /**
   *
   * 要匹配的值,用于 eq, neq, range, in, match
   *
   * eq 传 单个值对象，比如 Integer, String , etc
   * in 传 List 对象
   * range 传 Range 对象
   * match 传 Match 对象
   *
   */
  private Object value;

  public EsCondition() {
  }

  public EsCondition(String fieldName, Op op, Object value) {
    this.fieldName = fieldName;
    this.op = op;
    this.value = value;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Op getOp() {
    return op;
  }

  public Object getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "EsCondition{" +
           "fieldName='" + fieldName + '\'' +
           ", op=" + op +
           ", value=" + value +
           '}';
  }

  @Override
  public Condition and(Condition c) {
    return new CompositeMustCondition(Lists.newArrayList(c, this));
  }

  @Override
  public Condition or(Condition c, Integer shouldMinimumMatch) {
    List<Condition> shouldConditions = Lists.newArrayList(c, this);
    return new CompositeShouldCondition(shouldConditions, shouldMinimumMatch);
  }

  private static Map<String, String> op2EsKeyMap = ImmutableMap.of(
      Op.eq.name(), "term",
      Op.neq.name(), "term",
      Op.in.name(), "terms",
      Op.range.name(), "range",
      Op.match.name(), "match"
  );

  @Override
  public Map expr() {
    return buildEsExpr(op2EsKeyMap.get(op.name()));
  }

  private Map buildEsExpr(String esKey) {
    return ImmutableMap.of(esKey, ImmutableMap.of(fieldName, value));
  }

}
