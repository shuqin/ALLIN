package zzz.study.patterns.strategy.flow;

public interface ABizStrategy extends BizStrategy {

    /**
     * 计算整数的一半
     */
    int half(int a);

    @Override
    default String bizName() {
        return BizName.A.name();
    }
}
