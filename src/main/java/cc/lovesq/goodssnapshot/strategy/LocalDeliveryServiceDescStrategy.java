package cc.lovesq.goodssnapshot.strategy;

import cc.lovesq.constants.DeliveryType;
import cc.lovesq.goodssnapshot.impl4.ServiceTpl;
import cc.lovesq.model.Order;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2021/1/2 3:15 下午
 * @Created by qinshu
 */
@Component
public class LocalDeliveryServiceDescStrategy implements ServiceDescStrategy {
    @Override
    public boolean isSatisfied(Order order, ServiceTpl config) {
        return config.getKey().equals("localDelivery") && order.getDeliveryType() == DeliveryType.localDelivery;
    }

    @Override
    public String getDesc(Order order, ServiceTpl config) {
        return config.getTpl().replace("$startPrice", order.getLocalDeliveryBasePrice().toString())
                .replace("$deliveryPrice", order.getLocalDeliveryPrice().toString());
    }
}
