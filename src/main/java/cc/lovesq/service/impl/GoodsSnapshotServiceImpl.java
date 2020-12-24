package cc.lovesq.service.impl;

import cc.lovesq.dao.GoodsMapper;
import cc.lovesq.dao.OrderMapper;
import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.goodssnapshot.impl4.GoodsServiceSnapshotProgress;
import cc.lovesq.model.BookInfo;
import cc.lovesq.model.GoodsDO;
import cc.lovesq.model.Order;
import cc.lovesq.model.OrderDO;
import cc.lovesq.service.GoodsSnapshotService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class GoodsSnapshotServiceImpl implements GoodsSnapshotService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsServiceSnapshotProgress goodsServiceSnapshotProgress;

    @Override
    public boolean save(BookInfo bookInfo) {
        GoodsDO goodsDO = bookInfo.getGoods().toGoodsDO();
        goodsMapper.insert(goodsDO);
        OrderDO orderDO = bookInfo.getOrder().toOrderDO();
        orderMapper.insert(orderDO);
        return true;
    }

    @Override
    public List<GoodsServiceSnapshot> query(String orderNo) {
        Order order = queryByOrder(orderNo);
        return goodsServiceSnapshotProgress.getServiceDescs(order);
    }

    private Order queryByOrder(String orderNo) {
        return new Order();
    }


}
