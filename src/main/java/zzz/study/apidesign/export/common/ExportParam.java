package zzz.study.apidesign.export.common;

import lombok.Data;
import zzz.study.patterns.composite.escondition.Condition;

@Data
public class ExportParam {

  /** 调用方，必传 */
  private String source;

  /** 导出业务类型，必传 */
  private String bizType;

  /** 搜索参数，必传 */
  private SearchParam search;

  /** 请求ID，必传 */
  private String requestId;

  /** 导出ID, 运维使用 */
  private Long exportId;

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
}
