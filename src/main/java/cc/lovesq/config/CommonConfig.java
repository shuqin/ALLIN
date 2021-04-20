package cc.lovesq.config;

/**
 * 针对一类相似事件处理的通用配置部分
 *
 * 主要是对应 yml 中的 锚点和引用
 */
public class CommonConfig {

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

    /** serial 串行(不指定默认)  parallel 并发 */
    protected ExecWay way = ExecWay.serial;

    /** 组件参数类型 */
    protected String componentParamType;

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

    public void setModel(ExecutionModel model) {
        this.model = model;
    }

    public String getOriginParamType() {
        return originParamType;
    }

    public void setOriginParamType(String originParamType) {
        this.originParamType = originParamType;
    }

    public ExecWay getWay() {
        return way;
    }

    public void setWay(ExecWay way) {
        this.way = way;
    }

    public String getComponentParamType() {
        return componentParamType;
    }

    public void setComponentParamType(String componentParamType) {
        this.componentParamType = componentParamType;
    }
}
