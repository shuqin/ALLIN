package cc.lovesq.kafkamsg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Description kafka消息发送
 * @Date 2021/2/4 10:47 上午
 * @Created by qinshu
 */
@Component
public class KafkaMessageProducer {

    private static Log log = LogFactory.getLog(KafkaMessageProducer.class);

    private KafkaProducer producer;

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","localhost:9092");    // 指定 Broker
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");  // 将 key 的 Java 对象转成字节数组
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // 将 value 的 Java 对象转成字节数组
        properties.put("acks", "1");       // 消息至少成功发给一个副本后才返回成功
        properties.put("retries", "5");    // 消息重试 5 次

        producer = new KafkaProducer<String,String>(properties);

    }

    /**
     * 同步发送消息
     */
    public void send(ProducerRecord record) {
        try {
            producer.send(record).get(200, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    /**
     * 异步发送消息
     */
    public void sendAsync(ProducerRecord record, Callback callback) {
        try {
            producer.send(record, callback);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }
}
