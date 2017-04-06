package zzz.study.patterns.templateMethod.express;

/**
 * Created by shuqin on 17/4/6.
 */
public class ExpressParam {

  private String orderNo;  // 订单编号
  private String exId;     // 发货公司ID
  private String exNo;     // 发货单号

  public ExpressParam(String orderNo, String exId, String exNo) {
    this.orderNo = orderNo;
    this.exId = exId;
    this.exNo = exNo;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getExId() {
    return exId;
  }

  public void setExId(String exId) {
    this.exId = exId;
  }

  public String getExNo() {
    return exNo;
  }

  public void setExNo(String exNo) {
    this.exNo = exNo;
  }

  @Override
  public String toString() {
    return "ExpressParam{" +
           "orderNo='" + orderNo + '\'' +
           ", exId='" + exId + '\'' +
           ", exNo='" + exNo + '\'' +
           '}';
  }
}
