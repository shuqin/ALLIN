package zzz.study.patterns.composite.escondition;

/**
 * ES 操作符
 *
 * Created by shuqin on 17/6/5.
 */
public enum Op {

  eq,      // 等于一个具体的值, 字符串或数值. eg  kdt_id = 63077
  neq,     // 不等于一个具体的值, 字符串或数值 eg  activity_type != 13
  in,      // 值范围, 值可以为字符串或数值 eg state = [5,6,8,9]
  range,   // 时间范围, 值通常为时间戳 eg create_time range [1503537924, 1503538924]
  match,   // 匹配, eg. orderNo match "E2017"
  or;      // 或条件， eg kdt_id = 63077 or supplier_kdt_id = 63077
}
