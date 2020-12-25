package cc.lovesq.result.goodsnapshot;

import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.model.GoodsInfo;
import cc.lovesq.model.Order;

import java.util.List;

public class GoodsSnapshot {

    private GoodsInfo goodsInfo;

    private Order order;

    private List<GoodsServiceSnapshot> goodsServiceSnapshots;

    /** 订单金额，元为单位 */
    private Double priceYuan;

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<GoodsServiceSnapshot> getGoodsServiceSnapshots() {
        return goodsServiceSnapshots;
    }

    public void setGoodsServiceSnapshots(List<GoodsServiceSnapshot> goodsServiceSnapshots) {
        this.goodsServiceSnapshots = goodsServiceSnapshots;
    }

    public Double getPriceYuan() {
        return priceYuan;
    }

    public void setPriceYuan(Double priceYuan) {
        this.priceYuan = priceYuan;
    }
}
