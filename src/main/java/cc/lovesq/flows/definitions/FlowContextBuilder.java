package cc.lovesq.flows.definitions;

public interface FlowContextBuilder<E extends EventData> {

    /**
     * 根据事件数据构建事件处理的上下文语境
     */
    AbstractFlowContext build(E eventData);
}
