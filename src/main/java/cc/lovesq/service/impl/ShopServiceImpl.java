package cc.lovesq.service.impl;

import cc.lovesq.model.ShopModel;
import cc.lovesq.service.ShopService;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ShopServiceImpl implements ShopService {

    Random random = new Random(47);

    @Override
    public ShopModel getShopModel(Long shopId) {
        ShopModel shopModel = new ShopModel();
        shopModel.setHasRetailStore(random.nextBoolean());
        return shopModel;
    }
}
