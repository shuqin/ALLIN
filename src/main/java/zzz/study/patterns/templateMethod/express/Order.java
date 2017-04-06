package zzz.study.patterns.templateMethod.express;

/**
 * Created by shuqin on 17/4/6.
 */
public class Order {

  private String orderNo;
  private Integer orderType;

  public Order(String orderNo, Integer orderType) {
    this.orderNo = orderNo;
    this.orderType = orderType;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public Integer getOrderType() {
    return orderType;
  }

  public void setOrderType(Integer orderType) {
    this.orderType = orderType;
  }
}
