package cc.lovesq.config;

import java.util.List;

/**
 * 组件执行配置模型定义
 *
 * 事件处理流程实例标识：
 * 1.  bizType + eventType + eventSourceType
 * 2.  eventFlowClassName
 */
public class EventFlowExecutionModel extends ComponentsExecutionModel {

    /**
     * 事件流程处理的业务类型，比如反弹shell, 本地提权等
     * 由于一个通用事件处理流程可以处理多个业务类型，因此这里可以是列表
     */
    private List<String> bizTypes;

    /** 事件类型，比如创建，更新等 */
    private String eventType;

    /** 事件流程处理的事件源类型，比如 agent上报事件， bizEvent事件 */
    private String eventSourceType;

    /** 根据类名来选择事件处理流程实例 */
    private String eventflowClassName;

    /** eventflow 事件流程处理(不指定默认)  components 有序组件集执行 */
    private ExecutionModel model = ExecutionModel.eventflow;

    /** 原始传入事件处理流程的入口参数类型 */
    private String originParamType;


    public List<String> getBizTypes() {
        return bizTypes;
    }

    public void setBizTypes(List<String> bizTypes) {
        this.bizTypes = bizTypes;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventSourceType() {
        return eventSourceType;
    }

    public void setEventSourceType(String eventSourceType) {
        this.eventSourceType = eventSourceType;
    }

    public String getEventflowClassName() {
        return eventflowClassName;
    }

    public void setEventflowClassName(String eventflowClassName) {
        this.eventflowClassName = eventflowClassName;
    }

    public ExecutionModel getModel() {
        return model;
    }

    public void setModel(ExecutionModel execModel) {
        this.model = execModel;
    }

    public String getOriginParamType() {
        return originParamType;
    }

    public void setOriginParamType(String originParamType) {
        this.originParamType = originParamType;
    }
}
