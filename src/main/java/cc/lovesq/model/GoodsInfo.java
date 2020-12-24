package cc.lovesq.model;

public class GoodsInfo {

    /** 商品ID */
    private Long goodsId;

    /** 商品标题 */
    private String title;

    /** 商品描述 */
    private String desc;

    /** 商品服务 keys */
    private String serviceKeys;

    /** 商品规格选择 */
    private String choice;

    /** 商品关联的订单号 */
    private String orderNo;

    public GoodsDO toGoodsDO() {
        GoodsDO goodsDO = new GoodsDO();
        goodsDO.setGoodsId(goodsId);
        goodsDO.setTitle(title);
        goodsDO.setDesc(desc);
        goodsDO.setChoice(choice);
        goodsDO.setOrderNo(orderNo);
        return goodsDO;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getServiceKeys() {
        return serviceKeys;
    }

    public void setServiceKeys(String serviceKeys) {
        this.serviceKeys = serviceKeys;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
