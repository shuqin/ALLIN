package zzz.study.patterns.strategy.flow;

public interface BBizStrategy extends BizStrategy {

    /**
     * 计算整数的两倍
     */
    int multi(int a);

    @Override
    default String bizName() {
        return BizName.B.name();
    }
}
