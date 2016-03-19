package zzz.study.patterns.strategy.flow;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author qin.shu
 * @Date 2022/6/6
 * @Description
 */
public class CommonFlowStrategySelector implements FlowStrategySelector {

    private AtomicBoolean isExtremeFlow = new AtomicBoolean(false);

    private BizName biz;
    private PlainFlowStrategy plainFlowStrategy;
    private ExtremeFlowStrategy extremeFlowStrategy;

    public CommonFlowStrategySelector(BizName biz) {
        this.biz = biz;
        if (biz == BizName.A) {
            if (plainFlowStrategy == null) {
                plainFlowStrategy = new APlainStrategy();
            }
            if (extremeFlowStrategy == null) {
                extremeFlowStrategy = new AExtremeStrategy();
            }
        }
        else if (biz == BizName.B) {
            if (plainFlowStrategy == null) {
                plainFlowStrategy = new BPlainStrategy();
            }
            if (extremeFlowStrategy == null) {
                extremeFlowStrategy = new BExtremeStrategy();
            }
        }
    }

    public FlowStrategy selectInner() {
        return isExtremeFlow.get() ? extremeFlowStrategy : plainFlowStrategy;
    }

    @Override
    public <FS extends BizStrategy> FS select(Class<FS> cls) {
        return (FS)selectInner();
    }

    @Override
    public void notifyExtreme() {
        isExtremeFlow.compareAndSet(false, true);
    }

    @Override
    public void normal() {
        isExtremeFlow.compareAndSet(true, false);
    }
}
