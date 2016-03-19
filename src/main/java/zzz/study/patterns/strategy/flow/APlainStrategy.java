package zzz.study.patterns.strategy.flow;

/**
 * @author qin.shu
 * @Date 2022/6/5
 * @Description 普通流量策略
 */
public class APlainStrategy implements ABizStrategy, PlainFlowStrategy {
    @Override
    public int half(int a) {
        System.out.println("APlainStrategy");
        return a / 2 ;
    }
}
