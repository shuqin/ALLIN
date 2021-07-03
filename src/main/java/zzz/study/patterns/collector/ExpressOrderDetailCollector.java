package zzz.study.patterns.collector;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("expressOrderDetailCollector")
public class ExpressOrderDetailCollector implements OrderDetailCollector {

    @Override
    public void collect(List<OrderInfo> orderInfoList, List<OrderItemInfo> orderItemInfoList) {
        orderItemInfoList.forEach(
                orderItemInfo -> orderItemInfo.setExpressId("EXP")
        );
    }
}
