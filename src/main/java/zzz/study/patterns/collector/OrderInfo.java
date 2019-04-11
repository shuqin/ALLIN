package zzz.study.patterns.collector;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderInfo {

  private String orderNo;

  public OrderInfo(String orderNo) {
    this.orderNo = orderNo;
  }
}
