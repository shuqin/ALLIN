package cc.lovesq.components;

import cc.lovesq.experiments.goodssnapshot.GoodsSnapshotExperiment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduleTasks {

    ScheduledExecutorService es = Executors.newScheduledThreadPool(1);

    @Resource
    private GoodsSnapshotExperiment goodsSnapshotExperiment;

    @PostConstruct
    public void init() {
        es.scheduleAtFixedRate(
                (Runnable) () -> goodsSnapshotExperiment.test(),
                1000000, 1000000,
                TimeUnit.SECONDS
        );
    }
}
