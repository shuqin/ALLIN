package cc.lovesq.flows.definitions;

/**
 * 流程执行结果帮助类
 */
public class FlowResultHelper {

    private static FlowResult continueResult = new FlowResult(null, FlowDecision.Continue);
    private static FlowResult terminateResult = new FlowResult(null, FlowDecision.Termination);

    public static FlowResult continueResult() {
        return continueResult;
    }

    public static <T> FlowResult continueResult(T data) {
        return new FlowResult(data, FlowDecision.Continue);
    }

    public static FlowResult terminateResult() {
        return terminateResult;
    }

    public static <T> FlowResult terminateResult(T data) {
        return new FlowResult(data, FlowDecision.Termination);
    }

}
