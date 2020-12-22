package cc.lovesq.experiments.redis;

import cc.lovesq.experiments.IExperiment;
import cc.lovesq.model.AvoidingOversold;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("avoidingOversoldExperiment")
public class AvoidingOversoldExperiment implements IExperiment {

    ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(30);

    // 这里需要静态注入的方式，才能让 AOP 注解生效, 否则, 必须使用显式代理方式
    @Resource
    AvoidingOversold avoidingOversold;

    @Override
    public void test() {
        /*
        int i = 10000;
        while (i > 0) {
            threadPoolExecutor.submit(this::buy);
            i--;
        }
         */
    }

    public boolean buy() {
        return avoidingOversold.sell(avoidingOversold.getGoods());
    }
}
