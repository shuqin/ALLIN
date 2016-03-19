package cc.lovesq.flows.components;

import cc.lovesq.flows.definitions.ComponentProperties;
import cc.lovesq.flows.definitions.DetectEventEnum;
import cc.lovesq.flows.detect.bizevents.DefaultDetectFlowContext;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ComponentProperties(purpose = "BigDataSender")
@Component
public class DefaultDetectBigDataSender implements BigDataSender<DefaultDetectFlowContext> {

    private static Logger logger = LoggerFactory.getLogger(DefaultDetectBigDataSender.class);

    @Override
    public void sendEventMessage(DefaultDetectFlowContext context) {
        String bigDataType = DetectEventEnum.getBigDataType(context.getEventData().getDetectType());
        logger.info("send: bigDataType={}, msg={}", bigDataType, JSON.toJSONString(context.getEventData().getData()));
    }
}
