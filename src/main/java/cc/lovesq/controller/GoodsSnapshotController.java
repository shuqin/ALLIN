package cc.lovesq.controller;

import cc.lovesq.exception.Errors;
import cc.lovesq.exception.IError;
import cc.lovesq.model.BookInfo;
import cc.lovesq.result.BaseResult;
import cc.lovesq.service.GoodsSnapshotService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Arrays;

@Controller
@RequestMapping("/api/goodsnapshot/")
public class GoodsSnapshotController {

    private static Log log = LogFactory.getLog(CreativeController.class);

    @Resource
    private GoodsSnapshotService goodsSnapshotService;

    @RequestMapping(value = "/save")
    public BaseResult save(@RequestBody BookInfo bookInfo) {
        Assert.notNull(bookInfo, "商品对象不能为空");
        Assert.notNull(bookInfo.getGoods().getGoodsId(), "商品ID不能为空");
        bookInfo.getOrder().setKeys(Arrays.asList(bookInfo.getGoods().getServiceKeys().split(",")));
        bookInfo.getGoods().setOrderNo(bookInfo.getOrder().getOrderNo());
        boolean isSaved = goodsSnapshotService.save(bookInfo);
        return isSaved ? BaseResult.succ("保存成功"): BaseResult.failed(Errors.BookError);
    }

}
