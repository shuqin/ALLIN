package zzz.study.patterns.decorator.realpay;

import org.springframework.stereotype.Component;

@Component("limitedRealPayFormatter")
public class LimitedRealPayFormatter implements RealPayFormatter {

  @Override
  public Long format(Long realPay, OrderDetail context) {
    if (realPay >= 10000) {
      throw new IllegalArgumentException("RealPay Exceed 10000 , not allowed !");
    }
    return realPay;
  }
}
