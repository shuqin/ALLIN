package cc.lovesq.config;

import java.util.List;

/**
 * 事件处理流程执行模型定义
 */
public class EventFlowsModel {

    private String version;

    private List<EventFlowExecutionModel> flows;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<EventFlowExecutionModel> getFlows() {
        return flows;
    }

    public void setFlows(List<EventFlowExecutionModel> flows) {
        this.flows = flows;
    }
}
