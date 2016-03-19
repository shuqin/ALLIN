package zzz.study.patterns.collector;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectorClient {

    @Resource
    OrderDetailCollectorFactory orderDetailCollectorFactory;

    public void assembleReportOrders() {

        // 可以配置在 DB 或 Apollo 里
        List<String> collectors = Arrays.asList("baseOrderDetailCollector", "oldOrderDetailCollector", "orderPriceDetailCollector", "expressOrderDetailCollector");

        List<OrderInfo> orderInfos = new ArrayList<>();
        List<OrderItemInfo> orderItemInfos = new ArrayList<>();

        collectors.forEach(
                collector -> orderDetailCollectorFactory.get(collector).collect(orderInfos, orderItemInfos)
        );

        List<DefaultReportOrderInfo> reportOrderInfos = assembleByOrderNo(orderInfos, orderItemInfos);

    }

    private List<DefaultReportOrderInfo> assembleByOrderNo(List<OrderInfo> orderInfos, List<OrderItemInfo> orderItemInfos) {
        // 使用订单号作为关联来构造 DefaultReportOrderInfo 实例列表
        return new ArrayList<>();
    }
}




