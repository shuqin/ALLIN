package cc.lovesq.model.transfer;

import cc.lovesq.model.BookInfo;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.ProducerRecord;

import static cc.lovesq.constants.GlobalConstants.ORDER_EVENTS_TOPIC;

/**
 * @Description TODO
 * @Date 2021/2/4 11:16 上午
 * @Created by qinshu
 */
public class BookInfoToMessageTransfer {

    public static ProducerRecord transfer(BookInfo bookInfo) {
        String key = bookInfo.getOrder().getOrderNo();
        String value = JSON.toJSONString(bookInfo);
        ProducerRecord producerRecord = new ProducerRecord(ORDER_EVENTS_TOPIC, key, value);
        return producerRecord;
    }
}
