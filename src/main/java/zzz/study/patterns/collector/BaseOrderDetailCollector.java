package zzz.study.patterns.collector;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("baseOrderDetailCollector")
public class BaseOrderDetailCollector implements OrderDetailCollector {

    @Override
    public void collect(List<OrderInfo> orderInfoList, List<OrderItemInfo> orderItemInfoList) {
        orderInfoList.addAll(Arrays.asList(new OrderInfo("E001"), new OrderInfo("E002")));
        orderItemInfoList.addAll(Arrays.asList(new OrderItemInfo("E001", "I00001"), new OrderItemInfo("E002", "I000002")));
    }
}
