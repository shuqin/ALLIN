package cc.lovesq.kafkamsg;

import cc.lovesq.model.BookInfo;
import shared.utils.TimeUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @Description Kafka 事件流
 * @Date 2021/2/4 8:17 下午
 * @Created by qinshu
 */
@Component
public class KafkaMessageStream {

    private static Logger logger = LoggerFactory.getLogger(KafkaMessageStream.class);

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "orderCount");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder streamBuilder = new StreamsBuilder();
        KStream<String,String> source = streamBuilder.stream("order-events");

        KStream result = source.filter(
                (key, value) -> value.startsWith("{") && value.endsWith("}")
        ).mapValues(
                value -> JSONObject.parseObject(value, BookInfo.class)
        ).mapValues(
                bookInfo -> bookInfo.getGoods().getGoodsId().toString()
        ).groupBy((key,value) -> value).count(Materialized.as("goods-order-count")
        ).mapValues(value -> Long.toString(value)).toStream();

        result.print(Printed.toSysOut());

        new Thread(
                () -> {
                    TimeUtil.sleepInSecs(10);
                    KafkaStreams streams = new KafkaStreams(streamBuilder.build(), properties);
                    streams.start();
                    logger.info("stream-start ...");
                    TimeUtil.sleepInSecs(10);
                    streams.close();
                }
        ).start();

    }


}
