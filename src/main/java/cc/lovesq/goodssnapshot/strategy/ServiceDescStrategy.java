package cc.lovesq.goodssnapshot.strategy;

import cc.lovesq.goodssnapshot.impl4.ServiceTpl;
import cc.lovesq.model.Order;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2021/1/2 3:12 下午
 * @Created by qinshu
 */
@Component
public interface ServiceDescStrategy {

    boolean isSatisfied(Order order, ServiceTpl config);

    default String getDesc(Order order, ServiceTpl config) {
        return config.getTpl();
    }
}
