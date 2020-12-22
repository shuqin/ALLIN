package cc.lovesq.goodssnapshot;

import java.util.List;
import java.util.Objects;

public class Order {

    /** 订单号 */
    private String orderNo;

    /** 订单类型 0 普通 */
    private Integer orderType;

    /** 下单时间 */
    private Long bookTime;

    /** 是否担保交易 */
    private boolean isSecuredOrder;

    /** 配送方式 0 快递 1 自提 */
    private Integer deliveryType;

    /** 订单金额 */
    private Double price;

    /** 订单的服务 keys */
    private List<String> keys;

    private boolean isExpress() {
        return Objects.equals(deliveryType, 0);
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public Long getBookTime() {
        return bookTime;
    }

    public boolean isSecuredOrder() {
        return isSecuredOrder;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public Double getPrice() {
        return price;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public void setBookTime(Long bookTime) {
        this.bookTime = bookTime;
    }

    public void setSecuredOrder(boolean securedOrder) {
        isSecuredOrder = securedOrder;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }
}
