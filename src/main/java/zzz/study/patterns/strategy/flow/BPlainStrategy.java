package zzz.study.patterns.strategy.flow;

/**
 * @author qin.shu
 * @Date 2022/6/5
 * @Description 普通流量策略
 */
public class BPlainStrategy implements BBizStrategy, PlainFlowStrategy {
    @Override
    public int multi(int a) {
        System.out.println("BPlainStrategy");
        return a * 2 ;
    }
}
