package zzz.study.patterns.collector;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectorClient {

  @Resource
  OrderDetailCollectorFactory orderDetailCollectorFactory;

  public void usage() {

    // 可以配置在 DB 或 Apollo 里
    List<String> collectors = Arrays.asList("baseOrderDetailCollector", "expressOrderDetailCollector");

    List<OrderInfo> orderInfos = new ArrayList<>();
    List<OrderItemInfo> orderItemInfos = new ArrayList<>();

    collectors.forEach(
        collector -> orderDetailCollectorFactory.get(collector).collect(orderInfos, orderItemInfos)
    );

  }
}
