package cc.lovesq.flows.detect.bizevents;

import cc.lovesq.flows.components.DefaultThreatInfoSender;
import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cc.lovesq.flows.factory.DetectEventFlowFactory;
import cc.lovesq.flows.definitions.BizEventHandler;
import cc.lovesq.flows.definitions.EventFlow;
import cc.lovesq.flows.detect.DetectEventData;

/**
 * 入侵事件的 BizEvent 对象的处理流程入口
 *
 * 前置环节：
 * Agent 入侵事件上报经过业务处理后构建成 BizEvent 发送给 Kafka
 * BizEvent 对象从 Kafka 消息中获得。
 */
@Component
public class DetectEventHandler implements BizEventHandler {

    private static Log log = LogFactory.getLog(DefaultThreatInfoSender.class);

    private DetectEventFlowFactory detectEventFlowFactory;

    @Autowired
    public DetectEventHandler(DetectEventFlowFactory detectEventFlowFactory) {
        this.detectEventFlowFactory = detectEventFlowFactory;
    }

    @Override
    public void handle(BizEvent bizEvent) {
        try {
            if (null == bizEvent.getData() || !(bizEvent.getData() instanceof DetectEventData)) {
                log.warn("not-detect-data:" + JSON.toJSONString(bizEvent.getData()));
                return;
            }

            DetectEventData data = bizEvent.getData();

            String bizEventType = bizEvent.getBizEventType();
            String detectType = data.getEventType();
            EventFlow<DetectEventDataWrapper> eventFlow = detectEventFlowFactory.getEventFlow(bizEventType, detectType);
            eventFlow.process(new DetectEventDataWrapper(data));

        } catch (Exception e) {
            log.error("DetectEventHandler@exception", e);
        }
    }
}
