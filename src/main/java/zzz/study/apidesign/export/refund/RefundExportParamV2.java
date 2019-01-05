package zzz.study.apidesign.export.refund;


import lombok.Data;

@Data
class RefundExportParamV2 {

  /** 调用方 */
  private String source;

  /** 退款编号 */
  private String refundNo;

  /** 退款起始时间 */
  private Long startTime;

  /** 退款结束时间 */
  private Long endTime;

}
