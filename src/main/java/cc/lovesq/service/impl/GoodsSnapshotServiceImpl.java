package cc.lovesq.service.impl;

import cc.lovesq.dao.GoodsMapper;
import cc.lovesq.dao.OrderMapper;
import cc.lovesq.model.BookInfo;
import cc.lovesq.model.GoodsDO;
import cc.lovesq.model.OrderDO;
import cc.lovesq.service.GoodsSnapshotService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GoodsSnapshotServiceImpl implements GoodsSnapshotService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrderMapper orderMapper;

    @Override
    public boolean save(BookInfo bookInfo) {
        GoodsDO goodsDO = bookInfo.getGoods().toGoodsDO();
        goodsMapper.insert(goodsDO);
        OrderDO orderDO = bookInfo.getOrder().toOrderDO();
        orderMapper.insert(orderDO);
        return true;
    }


}
