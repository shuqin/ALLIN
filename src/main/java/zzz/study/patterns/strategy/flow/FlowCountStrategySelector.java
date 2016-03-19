package zzz.study.patterns.strategy.flow;

/**
 * @author qin.shu
 * @Date 2022/6/6
 * @Description
 */
public class FlowCountStrategySelector {

    private FlowCount flowCount;

    public FlowCountStrategy select(FlowStrategySelector flowStrategySelector) {
        if (flowCount == null) {
            flowCount = new FlowCount(flowStrategySelector);
        }
        return flowCount;
    }
}
