package zzz.study.patterns.decorator.realpay;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("giftRealPayFormatter")
public class GiftRealPayFormatter implements RealPayFormatter {
    @Override
    public Long format(Long realPay, OrderDetail orderDetail) {
        if (Objects.equals("GIFT", orderDetail.getBuyWay())) {
            return realPay;
        }
        return 0L;
    }
}
