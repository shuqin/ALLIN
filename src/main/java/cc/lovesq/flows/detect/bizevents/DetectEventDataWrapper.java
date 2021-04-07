package cc.lovesq.flows.detect.bizevents;

import cc.lovesq.flows.definitions.EventData;
import cc.lovesq.flows.detect.DetectEventData;

public class DetectEventDataWrapper implements EventData {

    DetectEventData detectEventData;

    public DetectEventDataWrapper(DetectEventData detectEventData) {
        this.detectEventData = detectEventData;
    }

    @Override
    public String getData() {
        return detectEventData.getEvent();
    }

    public String getType() {
        return detectEventData.getEventType();
    }

    public String getDetectType() { return detectEventData.getEventType(); }
}
