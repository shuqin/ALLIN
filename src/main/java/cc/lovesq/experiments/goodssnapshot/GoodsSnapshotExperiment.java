package cc.lovesq.experiments.goodssnapshot;

import cc.lovesq.constants.DeliveryType;
import cc.lovesq.experiments.Experiments;
import cc.lovesq.experiments.IExperiment;
import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.goodssnapshot.impl4.GoodsServiceSnapshotProgress;
import cc.lovesq.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component("goodsSnapshotExperiment")
public class GoodsSnapshotExperiment implements IExperiment {

    private static Logger logger = LoggerFactory.getLogger(Experiments.class);

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
        order.setPrice(0L);
        order.setBookTime(1598889600L);
        order.setIsSecuredOrder(true);
        order.setKeys(Arrays.asList("express", "secureService", "refundAndReturn"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress.getServiceDescs(order, order.getKeys());

        logger.info("{} {}", order.getOrderNo(), serviceSnapshots);
    }

    public void test2() {
        Order order = new Order();
        order.setOrderNo("E202010000000123400001");
        order.setDeliveryType(DeliveryType.express);
        order.setPrice(10L);
        order.setBookTime(1601481600L);
        order.setIsSecuredOrder(true);
        order.setKeys(Arrays.asList("express", "secureService", "refundAndReturn"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress.getServiceDescs(order, order.getKeys());

        logger.info("{} {}", order.getOrderNo(), serviceSnapshots);
    }

    public void test3() {
        Order order = new Order();
        order.setOrderNo("E202009000000123400001");
        order.setDeliveryType(DeliveryType.selfetch);
        order.setPrice(0L);
        order.setBookTime(1598889600L);
        order.setIsSecuredOrder(true);
        order.setKeys(Arrays.asList("selfetch", "secureService", "refundAndReturn"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress.getServiceDescs(order, order.getKeys());

        logger.info("{} {}", order.getOrderNo(), serviceSnapshots);
    }

    public void test4() {
        Order order = new Order();
        order.setOrderNo("E202009000000123400001");
        order.setDeliveryType(DeliveryType.localDelivery);
        order.setPrice(0L);
        order.setLocalDeliveryBasePrice(1L);
        order.setLocalDeliveryPrice(2L);
        order.setBookTime(1598889600L);
        order.setIsSecuredOrder(false);
        order.setIsCodPay(true);
        order.setHasRetailShop(true);
        order.setKeys(Arrays.asList("localDelivery", "secureService", "refundAndReturn", "codpay","retailShop", "drectoseller"));
        List<GoodsServiceSnapshot> serviceSnapshots =  goodsServiceSnapshotProgress.getServiceDescs(order, order.getKeys());

        logger.info("{} {}", order.getOrderNo(), serviceSnapshots);
    }
}
