package zzz.study.patterns.strategy.flow;

/**
 * @author qin.shu
 * @Date 2022/6/5
 * @Description 极端流量策略
 */
public class AExtremeStrategy implements ABizStrategy, ExtremeFlowStrategy {

    @Override
    public int half(int a) {
        System.out.println("AExtremeStrategy");
        return a >> 1;
    }
}
