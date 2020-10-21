package cc.lovesq.experiments.redis;

import cc.lovesq.components.DistributedLock;
import cc.lovesq.experiments.IExperiment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("avoidingOversoldExperiment")
public class AvoidingOversoldExperiment implements IExperiment {

    ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(30);

    @Resource(name="redissonLock")
    private DistributedLock distributedLock;

    @Override
    public void test() {

        AvoidingOversold avoidingOversold = new AvoidingOversold("Algorithm Course", 100, distributedLock);

        int i = 10000;
        while (i > 0) {
            threadPoolExecutor.submit(
                    avoidingOversold::sold
            );
            i--;
        }

    }
}
