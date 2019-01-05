package zzz.study.apidesign.export.common;

import java.util.Map;

import lombok.Data;
import zzz.study.patterns.composite.escondition.Condition;

@Data
public class ExportParam {

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
class SearchParam {

  /** 业务归属ID，必传 */
  private Long bizId;

  /** 搜索起始时间，必传 */
  private Long startTime;
  private Long endTime;

  /** 扩展搜索入参，可选 */
  private Condition condition;

  /** 扩展搜索入参，供 REST 调用，DSL查询构建见 ... */
  private String restCondition;
}
