package zzz.study.apidesign.export.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;
import zzz.study.patterns.composite.escondition.Match;
import zzz.study.patterns.composite.escondition.Op;
import zzz.study.patterns.composite.escondition.Range;

@Data
public class ExportParam implements Serializable {

  /** 调用方，必传 */
  private String source;

  /** 导出业务类型，必传 */
  private String bizType;

  /** 导出业务细分，必传 */
  private String category;

  /** 导出策略，默认 */
  private String strategy = "standard";

  /** 搜索参数，必传 */
  private SearchParam search;

  /** 请求ID，必传 */
  private String requestId;


  /** 导出选项，可用于定制化 */
  private Map<String, Object> options;

  /** 导出额外信息 */
  private Map<String, String> extra;

}

@Data
class SearchParam implements Serializable {

  /** 业务归属ID，必传 */
  private Long bizId;

  /** 搜索起始时间，必传 */
  private Long startTime;
  private Long endTime;

  /** 扩展搜索入参，可选 */
  private List<Condition> conditions;

}

@Data
class Condition implements Serializable {

  private static final long serialVersionUID = 7375091182172384776L;

  /** ES 字段 */
  private String fieldName;

  /** 操作符 */
  private Op op;

  /** 参数值 */
  private Object value;

  /** 范围对象传参 */
  private Range range;

  /** 匹配对象传参 */
  private Match match;

  // 为了让JsonMap 能走通，必须有一个默认构造器
  public Condition() {}

  public Condition(String fieldName, Op op, Object value) {
    this.fieldName = fieldName;
    this.op = op;
    this.value = value;
  }

}


