package zzz.study.patterns.strategy.flow;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author qin.shu
 * @Date 2022/6/5
 * @Description
 */
public class FlowStrategyTester {

    static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {

        FlowStrategySelectorFactory flowStrategySelectorFactory = new FlowStrategySelectorFactory();
        FlowStrategySelector aflowStrategySelector = flowStrategySelectorFactory.getSelector(BizName.A);
        FlowCountStrategySelector flowCountStrategySelector = new FlowCountStrategySelector();
        FlowCountStrategy aflowCount = flowCountStrategySelector.select(aflowStrategySelector);
        new Thread(aflowCount).start();

        for (int i=0; i < 200; i++) {
            int sleepTime = random.nextInt(50);
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            aflowStrategySelector.select(ABizStrategy.class).half(Integer.MAX_VALUE);
            aflowCount.rate(); // 可以通过消息队列来推送消息和计数
        }

        FlowStrategySelector bflowStrategySelector = flowStrategySelectorFactory.getSelector(BizName.B);
        FlowCountStrategy bflowCount = flowCountStrategySelector.select(bflowStrategySelector);
        new Thread(bflowCount).start();

        for (int i=0; i < 200; i++) {
            int sleepTime = random.nextInt(50);
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            bflowStrategySelector.select(BBizStrategy.class).multi(Integer.MAX_VALUE);
            bflowCount.rate(); // 可以通过消息队列来推送消息和计数
        }

    }

}
