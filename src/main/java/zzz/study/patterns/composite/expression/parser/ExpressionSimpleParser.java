package zzz.study.patterns.composite.expression.parser;

import org.apache.commons.lang.StringUtils;
import zzz.study.patterns.composite.expression.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 解析简易格式格式的表达式
 *
 * 条件与结果用 => 分开； 每个表达式之间用 ； 区分。
 *
 * SingleExpression: 单条件的一个表达式
 * state = 5 => 待发货
 *
 * CombinedExpression: 多条件的一个表达式
 * activity_type = 13 && state = 5 && tcExtra = null => 待开奖
 *
 * WholeExpression: 多个表达式的集合
 *
 * state = 5 => 待发货 ; activity = 13 && state = 50 => 待开奖
 *
 *
 */
public class ExpressionSimpleParser implements ExpressionParser {

  // 条件与结果之间的分隔符
  private static final String sep = "=>";

  // 复合条件之间之间的分隔符
  private static final String condSep = "&&";

  // 多个表达式之间的分隔符
  private static final String expSeq = ";";

  // 引号表示字符串
  private static final String quote = "\"";

  private static Pattern numberPattern = Pattern.compile("\\d+");

  private static Pattern listPattern = Pattern.compile("\\[(.*,?)+\\]");

  @Override
  public Expression parseSingle(String expStr) {
    check(expStr);
    String cond = expStr.split(sep)[0].trim();
    String result = expStr.split(sep)[1].trim();
    return new SingleExpression(parseCond(cond), result);
  }

  @Override
  public Expression parseCombined(String expStr) {
    check(expStr);
    String conds = expStr.split(sep)[0].trim();
    String result = expStr.split(sep)[1].trim();
    List<BaseCondition> conditions = Arrays.stream(conds.split(condSep)).filter(s -> StringUtils.isNotBlank(s)).map(this::parseCond).collect(Collectors.toList());
    return new CombinedExpression(new CombinedCondition(conditions), result);
  }

  @Override
  public Expression parseWhole(String expStr) {
    check(expStr);
    List<Expression> expressionList = Arrays.stream(expStr.split(expSeq)).filter(s -> StringUtils.isNotBlank(s)).map(this::parseExp).collect(Collectors.toList());
    return new WholeExpressions(expressionList);
  }

  private Expression parseExp(String expStr) {
    expStr = expStr.trim();
    return expStr.contains(condSep) ? parseCombined(expStr) : parseSingle(expStr);
  }

  private BaseCondition parseCond(String condStr) {
    condStr = condStr.trim();
    Set<String> allOps = Op.getAllOps();
    Optional<String> opHolder = allOps.stream().filter(condStr::contains).findFirst();
    if (!opHolder.isPresent()) {
      return null;
    }
    String op = opHolder.get();
    String[] fv = condStr.split(op);
    String field = fv[0].trim();
    String value = "";
    if (fv.length > 1) {
      value = condStr.split(op)[1].trim();
    }
    return new BaseCondition(field, Op.get(op), parseValue(value));
  }

  private Object parseValue(String value) {
    if (value.contains(quote)) {
      return value.replaceAll(quote, "");
    }
    if (numberPattern.matcher(value).matches()) {
      // 配置中通常不会用到长整型，因此这里直接转整型
      return Integer.parseInt(value);
    }
    if (listPattern.matcher(value).matches()) {
      String[] valueList = value.replace("[", "").replace("]","").split(",");
      List<Object> finalResult = Arrays.stream(valueList).map(this::parseValue).collect(Collectors.toList());
      return finalResult;
    }
    return value;
  }

  private void check(String expStr) {
    expStr = expStr.trim();
    if (StringUtils.isBlank(expStr) || !expStr.contains(sep)) {
      throw new IllegalArgumentException("expStr must contains => ");
    }
  }
}
