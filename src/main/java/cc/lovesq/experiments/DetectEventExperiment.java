package cc.lovesq.experiments;

import cc.lovesq.constants.EventType;
import cc.lovesq.constants.Severity;
import cc.lovesq.kafkamsg.KafkaMessageProducer;
import cc.lovesq.model.DetectEventDTO;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import shared.multitasks.customized.MyThreadPoolExecutor;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description 入侵检测事件实验
 * @Date 2021/5/7 10:12 上午
 * @Created by qinshu
 */
@Component
public class DetectEventExperiment implements IExperiment {

    static final Long EXPERIMENT_TIME = 1000 * 60 * 60 * 1L;
    static Random random = new Random(System.currentTimeMillis());
    @Resource
    KafkaMessageProducer kafkaMessageProducer;
    @Resource
    MyThreadPoolExecutor ioThreadPoolExecutor;
    EventType[] eventTypes = EventType.values();
    Severity[] severities = Severity.values();

    @Override
    public void test() {
        Long start = System.currentTimeMillis();
        while (true) {
            ioThreadPoolExecutor.execute(this::send);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                //
            }

            // EXPERIMENT_TIME 时间后结束实验
            if (System.currentTimeMillis() - start >= EXPERIMENT_TIME) {
                break;
            }
        }
        System.out.println("Exit DetectEventExperiment");

    }

    private void send() {
        int i = random.nextInt(4);
        int j = random.nextInt(4);
        EventType eventType = eventTypes[i];
        Severity severity = severities[j];
        DetectEventDTO detectEventDTO = new DetectEventDTO(eventType, severity);
        detectEventDTO.setEventTypeDesc(EventType.getEventDesc(eventType.getType()));
        detectEventDTO.setSeverityDesc(Severity.getSeverityDesc(severity.getValue()));
        kafkaMessageProducer.send(new ProducerRecord("detect-events", 0, detectEventDTO.getTimestamp(), null, JSON.toJSONString(detectEventDTO)));
    }
}
