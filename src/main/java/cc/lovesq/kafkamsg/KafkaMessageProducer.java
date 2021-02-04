package cc.lovesq.kafkamsg;

import cc.lovesq.model.Order;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @Description kafka消息发送
 * @Date 2021/2/4 10:47 上午
 * @Created by qinshu
 */
@Component
public class KafkaMessageProducer {

    private KafkaProducer producer;

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String,String>(properties);

    }

    public void send(ProducerRecord record) {
        producer.send(record);
    }
}
