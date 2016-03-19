package zzz.study.patterns.strategy.flow;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qin.shu
 * @Date 2022/6/5
 * @Description 流量计数器
 */
public class FlowCount implements FlowCountStrategy, Runnable {

    private FlowStrategySelector flowStrategySelector;
    private AtomicInteger rate = new AtomicInteger(0);
    private int count;
    private long lastStartTimestamp = System.currentTimeMillis();

    public FlowCount(FlowStrategySelector flowStrategySelector) {
        this.flowStrategySelector = flowStrategySelector;
    }

    @Override
    public void run() {
        while (true) {
            if (isExtreme()) {
                flowStrategySelector.notifyExtreme();
            } else {
                flowStrategySelector.normal();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    @Override
    public void rate() {
        count++;
        if (System.currentTimeMillis() - lastStartTimestamp >= 1000) {
            rate.getAndSet(count);
            lastStartTimestamp = System.currentTimeMillis();
            count = 0;
        }
    }

    @Override
    public boolean isExtreme() {
        return rate.get() > 40;
    }
}
