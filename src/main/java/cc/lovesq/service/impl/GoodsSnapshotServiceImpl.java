package cc.lovesq.service.impl;

import cc.lovesq.dao.GoodsMapper;
import cc.lovesq.dao.OrderMapper;
import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.goodssnapshot.impl4.GoodsServiceSnapshotProgress;
import cc.lovesq.model.*;
import cc.lovesq.query.GoodsQuery;
import cc.lovesq.query.GoodsSnapshotQuery;
import cc.lovesq.query.OrderQuery;
import cc.lovesq.result.goodsnapshot.GoodsSnapshot;
import cc.lovesq.service.GoodsSnapshotService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public boolean save(BookInfo bookInfo) {
        GoodsDO goodsDO = bookInfo.getGoods().toGoodsDO();
        goodsMapper.insert(goodsDO);
        OrderDO orderDO = bookInfo.getOrder().toOrderDO();
        orderMapper.insert(orderDO);
        return true;
    }

    @Override
    public GoodsSnapshot query(GoodsSnapshotQuery goodsSnapshotQuery) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setOrderNo(goodsSnapshotQuery.getOrderNo());
        OrderDO orderDO = orderMapper.findByQuery(orderQuery);
        Order order = Order.from(orderDO);
        List<GoodsServiceSnapshot> goodsServiceSnapshots = goodsServiceSnapshotProgress.getServiceDescs(order);

        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setOrderNo(goodsSnapshotQuery.getOrderNo());
        goodsQuery.setGoodsId(goodsSnapshotQuery.getGoodsId());
        GoodsDO goodsDO = goodsMapper.findByQuery(goodsQuery);

        GoodsSnapshot gs = new GoodsSnapshot();
        gs.setOrder(order);
        gs.setGoodsInfo(GoodsInfo.from(goodsDO));
        gs.setGoodsServiceSnapshots(goodsServiceSnapshots);
        return gs;

    }
}
