package cc.lovesq.goodssnapshot.strategy;

import cc.lovesq.goodssnapshot.impl4.ServiceTpl;
import cc.lovesq.model.Order;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2021/1/2 3:16 下午
 * @Created by qinshu
 */
@Component
public class CodPayServiceDescStrategy implements ServiceDescStrategy {

    @Override
    public boolean isSatisfied(Order order, ServiceTpl config) {
        return config.getKey().equals("codpay") && order.isCodPay();
    }
}
