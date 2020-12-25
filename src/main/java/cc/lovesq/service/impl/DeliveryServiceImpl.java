package cc.lovesq.service.impl;

import cc.lovesq.constants.DeliveryType;
import cc.lovesq.model.DeliveryModel;
import cc.lovesq.model.Order;
import cc.lovesq.service.DeliveryService;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DeliveryServiceImpl implements DeliveryService {

    Random random = new Random(47);

    @Override
    public DeliveryModel query(Order order) {
        DeliveryModel deliveryModel = new DeliveryModel();

        if (DeliveryType.express.equals(order.getDeliveryType())) {
            deliveryModel.setExpressFee(Long.valueOf(random.nextInt(8)));
            deliveryModel.setLocalDeliveryBasePrice(0L);
            deliveryModel.setLocalDeliveryPrice(0L);
            return deliveryModel;
        }

        if (DeliveryType.localDelivery.equals(order.getDeliveryType())) {
            deliveryModel.setExpressFee(0L);
            deliveryModel.setLocalDeliveryBasePrice(Long.valueOf(random.nextInt(5)));
            deliveryModel.setLocalDeliveryPrice(Long.valueOf(random.nextInt(10)));
            return deliveryModel;
        }

        deliveryModel.setExpressFee(0L);
        deliveryModel.setLocalDeliveryPrice(0L);
        deliveryModel.setLocalDeliveryBasePrice(0L);
        return deliveryModel;

    }
}
