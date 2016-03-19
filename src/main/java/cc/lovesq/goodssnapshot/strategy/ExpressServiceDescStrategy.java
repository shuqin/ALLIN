package cc.lovesq.goodssnapshot.strategy;

import cc.lovesq.constants.DeliveryType;
import cc.lovesq.goodssnapshot.impl4.ServiceTpl;
import cc.lovesq.model.Order;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2021/1/2 3:13 下午
 * @Created by qinshu
 */
@Component
public class ExpressServiceDescStrategy implements ServiceDescStrategy {
    @Override
    public boolean isSatisfied(Order order, ServiceTpl config) {
        return config.getKey().equals("express") && order.getDeliveryType() == DeliveryType.express;
    }

    @Override
    public String getDesc(Order order, ServiceTpl config) {
        // 如果订单运费是 0 元，则为 免运费，否则 运费 $info 元
        if (order.getExpressFee().equals(0L)) {
            return config.getTpl().replace("$info", "免运费");
        } else {
            return config.getTpl().replace("$info", "运费" + order.getExpressFee().toString());
        }
    }
}
