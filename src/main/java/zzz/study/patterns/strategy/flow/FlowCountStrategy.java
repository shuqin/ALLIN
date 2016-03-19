package zzz.study.patterns.strategy.flow;

/**
 * @author qin.shu
 * @Date 2022/6/6
 * @Description 流量计数策略
 */
public interface FlowCountStrategy extends Runnable {

    /**
     * 流量速率计算
     */
    void rate();

    /**
     * 是否极端流量
     */
    boolean isExtreme();
}
