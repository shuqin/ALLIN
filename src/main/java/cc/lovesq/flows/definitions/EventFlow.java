package cc.lovesq.flows.definitions;

public interface EventFlow<E extends EventData> {

    /**
     * 处理事件的流程
     */
    void process(E eventData);
}
