package zzz.study.patterns.strategy.flow;

public class FlowStrategySelectorFactory {

    public FlowStrategySelector getSelector(BizName biz) {
        return new CommonFlowStrategySelector(biz);
    }

}