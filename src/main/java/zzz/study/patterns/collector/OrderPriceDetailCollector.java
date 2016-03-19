package zzz.study.patterns.collector;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单商品价格信息获取
 */
@Component
public class OrderPriceDetailCollector implements OrderDetailCollector {
    @Override
    public void collect(List<OrderInfo> orderInfoList, List<OrderItemInfo> orderItemInfoList) {
        // 获取订单商品价格信息
    }
}
