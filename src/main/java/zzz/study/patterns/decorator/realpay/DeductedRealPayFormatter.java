package zzz.study.patterns.decorator.realpay;

import org.springframework.stereotype.Component;

@Component("deductedRealPayFormatter")
public class DeductedRealPayFormatter implements RealPayFormatter {
    @Override
    public Long format(Long realPay, OrderDetail orderDetail) {
        return realPay - 1L;
    }
}
