package zzz.study.patterns.strategy.flow;

/**
 * @author qin.shu
 * @Date 2022/6/5
 * @Description 极端流量策略
 */
public class BExtremeStrategy implements BBizStrategy, ExtremeFlowStrategy {

    @Override
    public int multi(int a) {
        System.out.println("BExtremeStrategy");
        return a << 1;
    }
}
