package cc.lovesq.flows.components;

import cc.lovesq.flows.definitions.ComponentProperties;
import cc.lovesq.flows.definitions.DetectDO;
import cc.lovesq.flows.definitions.DetectEventEnum;
import cc.lovesq.flows.detect.bizevents.DefaultDetectFlowContext;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 入侵事件处理：发送系统通知
 */
@ComponentProperties(purpose = "NotifySender")
@Component
public class DefaultDetectNotificationSender implements NotificationSender<DefaultDetectFlowContext> {

    private static Logger logger = LoggerFactory.getLogger(DefaultDetectBigDataSender.class);
    NotificationSendStrategy defaultDetectNotificationSender = new DefaultNotificationSendStrategy();

    @Override
    public void sendNotify(DefaultDetectFlowContext context) {
        String eventType = context.getEventData().getDetectType();
        if (get(eventType).needSend(context)) {
            String notifyType = DetectEventEnum.getNotifyType(eventType);
            DetectDO dto = context.getDetectDO();
            logger.info("sendNotification: notifyType={}, msg={}", notifyType, notifyType, JSON.toJSONString(dto));
        }
    }

    private NotificationSendStrategy get(String eventType) {
        return defaultDetectNotificationSender; //  use factory pattern
    }

    interface NotificationSendStrategy {
        boolean needSend(DefaultDetectFlowContext context);
    }

    static class DefaultNotificationSendStrategy implements NotificationSendStrategy {

        public boolean needSend(DefaultDetectFlowContext context) {
            return true;
        }

    }

}
