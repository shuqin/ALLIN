package cc.lovesq.experiments;

import cc.lovesq.flows.definitions.BizEventTypeEnum;
import cc.lovesq.flows.detect.DetectEventData;
import cc.lovesq.flows.detect.bizevents.BizEvent;
import cc.lovesq.flows.detect.bizevents.DetectEventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cc.lovesq.flows.definitions.DetectTypeEnum.BOUNCE_SHELL;

/**
 * @Description TODO
 * @Date 2021/4/7 9:35 下午
 * @Created by qinshu
 */
@Component
public class EventHandlerExperiment implements IExperiment {

    @Resource
    DetectEventHandler detectEventHandler;

    @Override
    public void test() {
        BizEvent bizEvent = new BizEvent();
        DetectEventData detectEventData = new DetectEventData();
        detectEventData.setEvent("{\"id\":123, \"agentGroup\": 222, \"createTime\": 1617802716}");
        detectEventData.setEventType(BOUNCE_SHELL.getType());
        bizEvent.setData(detectEventData);
        bizEvent.setBizEventType(BizEventTypeEnum.CREATE.getType());
        bizEvent.setDetectType(BOUNCE_SHELL.getType());

        detectEventHandler.handle(bizEvent);
    }
}
