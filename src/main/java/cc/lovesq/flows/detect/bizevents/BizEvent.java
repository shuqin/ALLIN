package cc.lovesq.flows.detect.bizevents;

import cc.lovesq.flows.detect.DetectEventData;

/**
 * @Description TODO
 * @Date 2021/4/7 9:30 下午
 * @Created by qinshu
 */
public class BizEvent {

    private DetectEventData data;

    private String bizEventType;

    private String detectType;

    public DetectEventData getData() {
        return data;
    }

    public void setData(DetectEventData data) {
        this.data = data;
    }

    public String getBizEventType() {
        return bizEventType;
    }

    public void setBizEventType(String bizEventType) {
        this.bizEventType = bizEventType;
    }

    public String getDetectType() {
        return detectType;
    }

    public void setDetectType(String detectType) {
        this.detectType = detectType;
    }
}
