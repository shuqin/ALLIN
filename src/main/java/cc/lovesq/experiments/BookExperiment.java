package cc.lovesq.experiments;

import cc.lovesq.constants.DeliveryType;
import cc.lovesq.controller.GoodsSnapshotController;
import cc.lovesq.kafkamsg.KafkaMessageProducer;
import cc.lovesq.model.BookInfo;
import cc.lovesq.model.GoodsInfo;
import cc.lovesq.model.Order;
import cc.lovesq.model.transfer.BookInfoToMessageTransfer;
import cc.lovesq.result.BaseResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 下单实验
 * @Date 2021/1/4 10:50 上午
 * @Created by qinshu
 */
@Component
public class BookExperiment implements IExperiment {

    private static Log log = LogFactory.getLog(BookExperiment.class);

    @Resource
    private GoodsSnapshotController goodsSnapshotController;

    @Resource
    private KafkaMessageProducer producer;

    private ExecutorService es = Executors.newFixedThreadPool(10);

    @Override
    public void test() {
        generateOrders();
    }

    // 线程池并发执行实验
    public void generateOrders() {
        for (int i=1; i < 1000; i++) {
            es.submit(() -> {
                book();
            });
        }
    }

    Random random = new Random(System.currentTimeMillis());

    private BaseResult book() {
        BookInfo bookInfo = new BookInfo();
        Order order = new Order();

        Long shopId = 654321L + random.nextInt(10000);
        Long userId = 1234L + random.nextInt(1000);
        Long goodsId = 5678L + random.nextInt(4000);
        order.setShopId(shopId);
        order.setUserId(userId);
        order.setDeliveryType(DeliveryType.express);
        order.setIsCodPay(false);
        bookInfo.setOrder(order);

        GoodsInfo goods = new GoodsInfo();
        goods.setGoodsId(goodsId);
        goods.setShopId(shopId);
        goods.setTitle("认养一头牛");
        goods.setDesc("2箱*250g");
        bookInfo.setGoods(goods);

        BaseResult bookResult = goodsSnapshotController.save(bookInfo);
        log.info("下单结果:" + JSON.toJSONString(bookResult));

        producer.sendAsync(
                BookInfoToMessageTransfer.transfer(bookInfo),
                (metadata, exception) -> callback(bookInfo, metadata, exception));

        return bookResult;
    }

    private void callback(BookInfo bookInfo, RecordMetadata metadata, Exception ex) {
        if (metadata != null) {
            log.info("发送订单消息:" + bookInfo.getOrder().getOrderNo() + " 偏移量: " + metadata.offset() + " 主题: " + metadata.topic());
        } else {
            log.error("发送订单消息失败: " + ex.getMessage(), ex);
        }
    }
}
