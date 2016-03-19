package cc.lovesq.service;

import cc.lovesq.model.DeliveryModel;
import cc.lovesq.model.Order;

public interface DeliveryService {

    DeliveryModel query(Order order);
}
