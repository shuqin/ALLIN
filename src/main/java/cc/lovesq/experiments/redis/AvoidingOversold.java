package cc.lovesq.experiments.redis;

import cc.lovesq.annotations.RemotingLock;
import cc.lovesq.components.DistributedLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.TimeUnit;

public class AvoidingOversold {

    private static Log log = LogFactory.getLog(AvoidingOversold.class);

    private String goods;
    private int permits;

    private DistributedLock distributedLock;

    public AvoidingOversold(String goods, int permits, DistributedLock distributedLock) {
        this.goods = goods;
        this.permits = 100;
        this.distributedLock = distributedLock;
    }

    public int sold() {
        return soldInner(goods);
    }

    @RemotingLock(time = 1, timeUnit = TimeUnit.SECONDS)
    public int soldInner(String goods) {
        if (permits > 0) {
            permits = permits - 1;
        }
        log.info("Rest Permits: " + permits);
        return permits;
    }
}
