package zzz.study.patterns.collector;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 老订单信息获取
 */
@Component
public class OldOrderDetailCollector implements OrderDetailCollector {

    @Override
    public void collect(List<OrderInfo> orderInfoList, List<OrderItemInfo> orderItemInfoList) {
        // obtain old order info and old order item info
    }
}
