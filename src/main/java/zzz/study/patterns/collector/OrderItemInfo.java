package zzz.study.patterns.collector;

import lombok.Data;

@Data
public class OrderItemInfo {

  private String orderNo;
  private String itemId;

  private String expressId;

  public OrderItemInfo(String orderNo, String itemId) {
    this.orderNo = orderNo;
    this.itemId = itemId;
  }
}
