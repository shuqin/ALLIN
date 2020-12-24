package cc.lovesq.controller;

import cc.lovesq.exception.Errors;
import cc.lovesq.model.BookInfo;
import cc.lovesq.query.GoodsSnapshotQuery;
import cc.lovesq.result.BaseResult;
import cc.lovesq.result.goodsnapshot.BookSaveResponse;
import cc.lovesq.result.goodsnapshot.GoodsSnapshot;
import cc.lovesq.service.GoodsSnapshotService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;

@Controller
@RequestMapping("/api/goodsnapshot/")
public class GoodsSnapshotController {

    private static Log log = LogFactory.getLog(CreativeController.class);

    @Resource
    private GoodsSnapshotService goodsSnapshotService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public BaseResult save(@RequestBody BookInfo bookInfo) {
        Assert.notNull(bookInfo, "商品对象不能为空");
        Assert.notNull(bookInfo.getGoods().getGoodsId(), "商品ID不能为空");
        bookInfo.getOrder().setKeys(Arrays.asList(bookInfo.getGoods().getServiceKeys().split(",")));
        bookInfo.getGoods().setOrderNo(bookInfo.getOrder().getOrderNo());
        boolean isSaved = goodsSnapshotService.save(bookInfo);
        BookSaveResponse bookSaveResponse = new BookSaveResponse(bookInfo.getOrder().getOrderNo(), bookInfo.getGoods().getGoodsId());
        return isSaved ? BaseResult.succ(bookSaveResponse): BaseResult.failed(Errors.BookError);
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
