package zzz.study.patterns.collector;

import java.util.List;

public interface OrderDetailCollector {
    void collect(List<OrderInfo> orderInfoList, List<OrderItemInfo> orderItemInfoList);
}
