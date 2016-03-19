package zzz.study.patterns.collector;

import java.util.List;

/**
 * 订单详情收集器接口
 */
public interface OrderDetailCollector {
    void collect(List<OrderInfo> orderInfoList, List<OrderItemInfo> orderItemInfoList);
}
