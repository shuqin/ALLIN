package cc.lovesq.model;

public class BookInfo {

    /** 下单的商品信息 */
    private GoodsInfo goods;

    /** 下单的订单信息 */
    private Order order;

    public GoodsInfo getGoods() {
        return goods;
    }

    public void setGoods(GoodsInfo goods) {
        this.goods = goods;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
