package zzz.study.apidesign.export.refund;

import lombok.Data;

@Data
public class RefundExportParamV4 {

  /** 调用方，必传 */
  private String source;

  /** 请求ID，必传 */
  private String requestId;

  /** 导出ID, 运维使用 */
  private Long exportId;

  /** 退款搜索参数 */
  private RefundSearchParam search;

}

