package cc.lovesq.model;

import cc.lovesq.constants.EventType;
import cc.lovesq.constants.Severity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2021/5/20 10:25 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class DetectEventDTO implements Serializable {

    private String eventType;
    private long timestamp;
    private int severity;
    private String eventTypeDesc;
    private String severityDesc;

    public DetectEventDTO() {
    }

    public DetectEventDTO(String eventType, long timestamp, int severity, String eventTypeDesc, String severityDesc) {
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.severity = severity;
        this.eventTypeDesc = eventTypeDesc;
        this.severityDesc = severityDesc;
    }

    public DetectEventDTO(String eventType, long timestamp, int severity) {
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.severity = severity;
    }

    public DetectEventDTO(EventType eventType, Severity severity) {
        this.eventType = eventType.getType();
        this.timestamp = System.currentTimeMillis();
        this.severity = severity.getValue();
    }

    public String toString() {
        return eventType + "," + timestamp + "," + severity + "," + eventTypeDesc + "," + severityDesc;
    }
}
