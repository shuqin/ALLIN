package cc.lovesq.flows.components;

import cc.lovesq.flows.definitions.ComponentProperties;
import cc.lovesq.flows.definitions.DetectDTO;
import cc.lovesq.flows.detect.bizevents.DefaultDetectFlowContext;
import cc.lovesq.flows.detect.model.ThreatEvent;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description 威胁情报发送
 * @Date 2021/4/7 4:05 下午
 * @Created by qinshu
 */
@ComponentProperties(purpose = "ThreatInfoSender")
@Component
public class DefaultThreatInfoSender implements ThreatInfoSender<DefaultDetectFlowContext> {

    private static Logger logger = LoggerFactory.getLogger(DefaultThreatInfoSender.class);

    @Override
    public void sendThreatMessage(DefaultDetectFlowContext context) {
        // 功能开关
        DetectDTO detectDTO = context.getDetectDTO();

        if (detectDTO == null) {
            return;
        }

        ThreatEvent event = from(context);
        logger.info("threat event: " + JSON.toJSONString(event));

    }

    private ThreatEvent from(DefaultDetectFlowContext context) {
        DetectDTO detectDTO = context.getDetectDTO();
        ThreatEvent event = new ThreatEvent();
        try {
            BeanUtils.copyProperties(event, detectDTO);
        } catch (Exception ex) {

        }


        return event;
    }
}
