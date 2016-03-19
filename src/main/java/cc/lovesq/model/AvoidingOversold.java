package cc.lovesq.model;

import cc.lovesq.annotations.RemotingLock;
import cc.lovesq.exception.BizException;
import cc.lovesq.exception.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

public class AvoidingOversold {

    private static Log log = LogFactory.getLog(AvoidingOversold.class);

    private String goods;
    private int permits;

    public AvoidingOversold(String goods, int permits) {
        this.goods = goods;
        this.permits = 100;
    }

    public String getGoods() {
        return goods;
    }

    @RemotingLock(time = 10, timeUnit = TimeUnit.SECONDS)
    public boolean sell(String goods) {
        try {
            if (permits > 0) {
                permits = permits - 1;
                log.info("Rest Permits: " + permits);
                return true;
            }
            throw new BizException(Errors.NoGoodsError);
        } catch (BizException e) {
            log.error(e.getMessage(), e);
            return false;
        }

    }

}
