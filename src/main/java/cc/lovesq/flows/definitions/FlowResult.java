package cc.lovesq.flows.definitions;

/**
 * @Description TODO
 * @Date 2021/4/10 12:29 下午
 * @Created by qinshu
 */
public class FlowResult<T> {

    private T result;
    private FlowDecision flowDecision;

    public FlowResult(T result, FlowDecision flowDecision) {
        this.result = result;
        this.flowDecision = flowDecision;
    }

    public T getResult() {
        return result;
    }

    public FlowDecision getFlowDecision() {
        return flowDecision;
    }
}
