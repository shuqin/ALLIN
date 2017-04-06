package zzz.study.patterns.templateMethod.express;

/**
 * Created by shuqin on 17/4/6.
 */
public class FenxiaoExpress extends AbstractExpress {

  public Order getOrder(String orderNo) {
    return new Order(orderNo, 5);
  }

  public void checkOrder(Order order) {
    if (Integer.valueOf(5).equals(order.getOrderType())) {
      throw new IllegalArgumentException("Fenxiao order can not express by own");
    }
  }

}
