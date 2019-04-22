package zzz.study.patterns.decorator.realpay;

public class RealPayFormatterClient {

  public static void main(String[] args) {

    Long realPay = 1000L;
    OrderDetail orderDetail = new OrderDetail(1, "GIFT");

    DeductedRealPayFormatter deductedRealPayFormatter = new DeductedRealPayFormatter();
    GiftRealPayFormatter giftRealPayFormatter = new GiftRealPayFormatter();
    LimitedRealPayFormatter limitedRealPayFormatter = new LimitedRealPayFormatter();

    Long realPay1 = giftRealPayFormatter.format(deductedRealPayFormatter.format(realPay, orderDetail), orderDetail);
    System.out.println("deducted and gift: " + realPay1);

    Long realPay2 = deductedRealPayFormatter.format(limitedRealPayFormatter.format(realPay, orderDetail), orderDetail);
    System.out.println("limited and gift: " + realPay2);

    realPay = 100000L;
    Long realPay3 = deductedRealPayFormatter.format(limitedRealPayFormatter.format(realPay, orderDetail), orderDetail);
    System.out.println("limited and gift: " + realPay3);
  }
}
