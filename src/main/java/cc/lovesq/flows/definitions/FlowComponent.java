package cc.lovesq.flows.definitions;

/**
 * 事件处理流程中的一个业务组件
 */
public interface FlowComponent<I extends AbstractFlowContext> {
    FlowResult process(I flowContext);

    // 满足此条件才能执行该组件，默认可以执行

    default boolean needAccess(I flowContext) {
        return true;
    }
}
