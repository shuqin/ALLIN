package cc.lovesq.goodssnapshot.strategy;

import cc.lovesq.goodssnapshot.impl4.ServiceTpl;
import cc.lovesq.model.Order;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2021/1/2 3:26 下午
 * @Created by qinshu
 */
@Component
public class SecuredOrderServiceDescStrategy implements ServiceDescStrategy {
    @Override
    public boolean isSatisfied(Order order, ServiceTpl config) {
        return "secureService".equals(config.getKey()) && order.isSecuredOrder();
    }
}
