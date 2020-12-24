package cc.lovesq.result.goodsnapshot;

public class BookSaveResponse {

    private String orderNo;

    private Long goodsId;

    public BookSaveResponse() {}

    public BookSaveResponse(String orderNo, Long goodsId) {
        this.orderNo = orderNo;
        this.goodsId = goodsId;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
