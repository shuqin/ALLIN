package cc.lovesq.flinkdemo;

import cc.lovesq.constants.EventType;
import cc.lovesq.constants.Severity;
import cc.lovesq.model.DetectEventDTO;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Properties;

/**
 * @Description kafka flink 统计示例
 * @Date 2021/5/7 2:52 下午
 * @Created by qinshu
 */
//@Component
public class KafkaFlinkDemo {

    @PostConstruct
    public void init() {
        new Thread(this::startUp).start();
    }

    public void startUp() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000); // 非常关键，一定要设置启动检查点！！
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "KafkaFlinkDemo");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  //key 反序列化
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("auto.offset.reset", "latest"); //value 反序列化

        FlinkKafkaConsumer011<String> consumer =new FlinkKafkaConsumer011<String>(
                "detect-events",  // kafka topic
                new SimpleStringSchema(),  // String 序列化
                props);

        //设置水位线
        consumer.assignTimestampsAndWatermarks(new MessageWaterEmitter());

        DataStreamSource<String> origin = env.addSource(consumer);
        DataStream<DetectEventDTO> detectEventDataStream = origin.map(new MessageMapper());
        writeToMongo(detectEventDataStream);

        countDangerEvents(detectEventDataStream);

        try {
            env.execute("Detect Event Statistics");
        } catch (Exception ex) {
            //
        }
    }

    private void writeToMongo(DataStream<DetectEventDTO> detectEventDataStream) {
        DataStream<Tuple3<String, String, Integer>> eventTypeCountStream =
                calc(detectEventDataStream, DetectEventDTO::getEventType, EventType::getEventDesc);

        DataStream<Tuple3<String, String, Integer>> severityCountStream =
                calc(detectEventDataStream, o -> String.valueOf(o.getSeverity()), Severity::getSeverityDesc);

        eventTypeCountStream.addSink(new MongoRepositorySinkFunction("eventType"));
        severityCountStream.addSink(new MongoRepositorySinkFunction("severity"));
    }

    private void countDangerEvents(DataStream<DetectEventDTO> detectEventDataStream) {
        DataStream<DetectEventDTO> dangerEvents =
                detectEventDataStream.filter( e -> e.getSeverity() >= 3);
        //DataStream<Tuple3<String, String, Integer>> severityCountStream =
        //        calc(dangerEvents, o -> String.valueOf(o.getSeverity()), Severity::getSeverityDesc);
        dangerEvents.print();
    }

    private DataStream<Tuple3<String, String, Integer>> calc(DataStream<DetectEventDTO> ds,
                                                             KeySelector<DetectEventDTO, String> keySelector,
                                                             SerializableFunction<String, String> mapperFunc) {
        CalcWindowFunction calcWindowFunction = new CalcWindowFunction(mapperFunc);
        return ds.keyBy(keySelector)
                .timeWindow(Time.seconds(10))
                //10秒统计数据并做均值计算
                .apply(calcWindowFunction);
    }

    public static class CalcWindowFunction
            implements Serializable, WindowFunction<DetectEventDTO, Tuple3<String, String, Integer>, String, TimeWindow> {

        SerializableFunction<String, String> function;

        public CalcWindowFunction(SerializableFunction<String, String> function) {
            this.function = function;
        }

        @Override
        public void apply(String s, TimeWindow timeWindow, Iterable<DetectEventDTO> events, Collector<Tuple3<String, String, Integer>> collector) throws Exception {
            int count = 0;
            Tuple3<String,String,Integer> result = new Tuple3<>(s, function.apply(s), count);
            for (DetectEventDTO e: events) {
                count++;
            }
            result.f2 = count;
            collector.collect(result);
        }
    }

}
