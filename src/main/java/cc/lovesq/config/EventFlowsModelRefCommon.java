package cc.lovesq.config;

import java.util.List;

public class EventFlowsModelRefCommon {

    private String version;

    private List<EventFlowExecutionModelRefCommon> flows;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<EventFlowExecutionModelRefCommon> getFlows() {
        return flows;
    }

    public void setFlows(List<EventFlowExecutionModelRefCommon> flows) {
        this.flows = flows;
    }
}
