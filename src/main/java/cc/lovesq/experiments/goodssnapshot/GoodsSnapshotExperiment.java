package cc.lovesq.experiments.goodssnapshot;

import cc.lovesq.constants.DeliveryType;
import cc.lovesq.experiments.Experiments;
import cc.lovesq.experiments.IExperiment;
import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.goodssnapshot.Order;
import cc.lovesq.goodssnapshot.impl4.GoodsServiceSnapshotProgress;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component("goodsSnapshotExperiment")
public class GoodsSnapshotExperiment implements IExperiment {

    private static Log log = LogFactory.getLog(Experiments.class);

    @Resource
    private GoodsServiceSnapshotProgress goodsServiceSnapshotProgress;

    @Override
    public void test() {
        test1();
        test2();
        test3();
        test4();
    }

    public void test1() {
        Order order = new Order();
        order.setOrderNo("E202009000000123400001");
        order.setDeliveryType(DeliveryType.express);
        order.setPrice(0.0);
        order.setBookTime(1598889600L);
        order.setSecuredOrder(true);
        order.setKeys(Arrays.asList("express", "secureService", "refundAndReturn"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress.getServiceDescs(order, order.getKeys());

        log.info(order.getOrderNo() + " " + serviceSnapshots);
    }

    public void test2() {
        Order order = new Order();
        order.setOrderNo("E202010000000123400001");
        order.setDeliveryType(DeliveryType.express);
        order.setPrice(0.10);
        order.setBookTime(1601481600L);
        order.setSecuredOrder(true);
        order.setKeys(Arrays.asList("express", "secureService", "refundAndReturn"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress.getServiceDescs(order, order.getKeys());

        log.info(order.getOrderNo() + " " + serviceSnapshots);
    }

    public void test3() {
        Order order = new Order();
        order.setOrderNo("E202009000000123400001");
        order.setDeliveryType(DeliveryType.selfetch);
        order.setPrice(0.0);
        order.setBookTime(1598889600L);
        order.setSecuredOrder(true);
        order.setKeys(Arrays.asList("selfetch", "secureService", "refundAndReturn"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress.getServiceDescs(order, order.getKeys());

        log.info(order.getOrderNo() + " " + serviceSnapshots);
    }

    public void test4() {
        Order order = new Order();
        order.setOrderNo("E202009000000123400001");
        order.setDeliveryType(DeliveryType.localDelivery);
        order.setPrice(0.0);
        order.setLocalDeliveryBasePrice(0.01);
        order.setLocalDeliveryPrice(0.02);
        order.setBookTime(1598889600L);
        order.setSecuredOrder(false);
        order.setCodPay(true);
        order.setHasRetailShop(true);
        order.setKeys(Arrays.asList("localDelivery", "secureService", "refundAndReturn", "codpay","retailShop", "drectoseller"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress.getServiceDescs(order, order.getKeys());

        log.info(order.getOrderNo() + " " + serviceSnapshots);
    }
}
