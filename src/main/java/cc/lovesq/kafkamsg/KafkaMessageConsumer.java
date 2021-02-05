package cc.lovesq.kafkamsg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Description kafka消息接收
 * @Date 2021/2/4 11:04 上午
 * @Created by qinshu
 */
@Component
public class KafkaMessageConsumer {

    private static Log log = LogFactory.getLog(KafkaMessageConsumer.class);

    private KafkaConsumer consumer;

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","localhost:9092");  // 指定 Broker
        properties.put("group.id", "experiment");              // 指定消费组群 ID
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // 将 key 的字节数组转成 Java 对象
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  // 将 value 的字节数组转成 Java 对象

        consumer = new KafkaConsumer(properties);
        consumer.subscribe(Collections.singleton("order-events"));  // 订阅主题 order-events

        new Thread(this::consumer).start();
    }

    public void consumer() {
        try {
            while (true) {
                ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String,String> record: records) {
                    String info = String.format("[Topic: %s][Partition:%d][Offset:%d][Key:%s][Message:%s]",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                    log.info("Received:" + info);
                }
            }
        } finally {
            consumer.close();
        }

    }
}
