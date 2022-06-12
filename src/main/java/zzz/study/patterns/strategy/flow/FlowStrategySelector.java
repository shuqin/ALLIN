package zzz.study.patterns.strategy.flow;

/**
 * @author qin.shu
 * @Date 2022/6/5
 * @Description 流量选择器工厂
 */
public interface FlowStrategySelector {

    <FS extends BizStrategy> FS select(Class<FS> cls);

    void notifyExtreme();

    void normal();



}
