package cc.lovesq.controller;

import cc.lovesq.annotations.Timecost;
import cc.lovesq.components.OrderGenerator;
import cc.lovesq.exception.Errors;
import cc.lovesq.model.*;
import cc.lovesq.query.GoodsSnapshotQuery;
import cc.lovesq.result.BaseResult;
import cc.lovesq.result.goodsnapshot.BookSaveResponse;
import cc.lovesq.result.goodsnapshot.GoodsSnapshot;
import cc.lovesq.service.DeliveryService;
import cc.lovesq.service.GoodsSnapshotService;
import cc.lovesq.service.ShopService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

@Controller
@RequestMapping("/api/goodsnapshot/")
public class GoodsSnapshotController {

    private static Log log = LogFactory.getLog(GoodsSnapshotController.class);

    @Resource
    private GoodsSnapshotService goodsSnapshotService;

    @Resource
    private ShopService shopService;

    @Resource
    private DeliveryService deliveryService;

    @Timecost
    @RequestMapping(value = "/save")
    @ResponseBody
    public BaseResult save(@RequestBody BookInfo bookInfo) {
        Assert.notNull(bookInfo, "商品对象不能为空");
        Assert.notNull(bookInfo.getGoods().getGoodsId(), "商品ID不能为空");

        complete(bookInfo);
        boolean isSaved = goodsSnapshotService.save(bookInfo);
        BookSaveResponse bookSaveResponse = new BookSaveResponse(bookInfo.getOrder().getOrderNo(), bookInfo.getGoods().getGoodsId());
        return isSaved ? BaseResult.succ(bookSaveResponse): BaseResult.failed(Errors.BookError);
    }

    private void complete(BookInfo bookInfo) {
        Order order = bookInfo.getOrder();
        GoodsInfo goodsInfo = bookInfo.getGoods();

        Date date = new Date();
        String orderNo = OrderGenerator.generateOrder(date, order.getUserId());
        order.setOrderNo(orderNo);
        goodsInfo.setOrderNo(orderNo);
        order.setBookTime(date.getTime()/1000);
        order.setKeys(Arrays.asList(bookInfo.getGoods().getServiceKeys().split(",")));
        goodsInfo.setOrderNo(bookInfo.getOrder().getOrderNo());

        ShopModel shopModel = shopService.getShopModel(bookInfo.getGoods().getShopId());
        order.setHasRetailShop(shopModel.hasRetailStore());
        order.setIsSecuredOrder(shopModel.isSecuredShop());

        DeliveryModel deliveryModel = deliveryService.query(bookInfo.getOrder());
        order.setExpressFee(deliveryModel.getExpressFee());
        order.setLocalDeliveryPrice(deliveryModel.getLocalDeliveryPrice());
        order.setLocalDeliveryBasePrice(deliveryModel.getLocalDeliveryBasePrice());

    }


    @RequestMapping(value = "/detail")
    @ResponseBody
    public BaseResult detail(@RequestBody GoodsSnapshotQuery goodsSnapshotQuery) {
        Assert.notNull(goodsSnapshotQuery.getOrderNo(), "订单号不能为空");
        Assert.notNull(goodsSnapshotQuery.getGoodsId(), "商品ID不能为空");
        GoodsSnapshot goodsSnapshot = goodsSnapshotService.query(goodsSnapshotQuery);
        return BaseResult.succ(goodsSnapshot);
    }

}
