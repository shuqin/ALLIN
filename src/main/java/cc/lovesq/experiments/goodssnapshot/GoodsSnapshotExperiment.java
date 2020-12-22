package cc.lovesq.experiments.goodssnapshot;

import cc.lovesq.experiments.IExperiment;
import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.goodssnapshot.Order;
import cc.lovesq.goodssnapshot.impl4.GoodsServiceSnapshotProgress4;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component("goodsSnapshotExperiment")
public class GoodsSnapshotExperiment implements IExperiment {

    @Resource
    private GoodsServiceSnapshotProgress4 goodsServiceSnapshotProgress4;

    @Override
    public void test() {
        Order order = new Order();
        order.setOrderNo("E202009000000123400001");
        order.setDeliveryType(0);
        order.setOrderType(0);
        order.setPrice(0.0);
        order.setBookTime(1608598336L);
        order.setSecuredOrder(true);
        order.setKeys(Arrays.asList("express", "secureService", "refundAndReturn"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress4.getServiceDescs2(order, order.getKeys());

        System.out.println(serviceSnapshots);
    }
}
