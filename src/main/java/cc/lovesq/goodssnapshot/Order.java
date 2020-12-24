package cc.lovesq.goodssnapshot;

import cc.lovesq.constants.DeliveryType;

import java.util.List;
import java.util.Objects;

public class Order {

    /** 订单号 */
    private String orderNo;

    /** 下单时间 */
    private Long bookTime;

    /** 是否货到付款 */
    private boolean isCodPay;

    /** 是否担保交易 */
    private boolean isSecuredOrder;

    /** 是否有线下门店 */
    private boolean hasRetailShop;

    /** 配送方式 0 快递 1 自提 2 同城送 */
    private DeliveryType deliveryType;

    /** 订单金额 */
    private Double price;

    /** 同城配送起送金额 */
    private Double localDeliveryBasePrice;

    /** 同城配送金额 */
    private Double localDeliveryPrice;


    /** 订单的服务 keys */
    private List<String> keys;

    private boolean isExpress() {
        return Objects.equals(deliveryType, 0);
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getBookTime() {
        return bookTime;
    }

    public void setBookTime(Long bookTime) {
        this.bookTime = bookTime;
    }

    public boolean isCodPay() {
        return isCodPay;
    }

    public void setCodPay(boolean codPay) {
        isCodPay = codPay;
    }

    public boolean isSecuredOrder() {
        return isSecuredOrder;
    }

    public void setSecuredOrder(boolean securedOrder) {
        isSecuredOrder = securedOrder;
    }

    public boolean isHasRetailShop() {
        return hasRetailShop;
    }

    public void setHasRetailShop(boolean hasRetailShop) {
        this.hasRetailShop = hasRetailShop;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getLocalDeliveryBasePrice() {
        return localDeliveryBasePrice;
    }

    public void setLocalDeliveryBasePrice(Double localDeliveryBasePrice) {
        this.localDeliveryBasePrice = localDeliveryBasePrice;
    }

    public Double getLocalDeliveryPrice() {
        return localDeliveryPrice;
    }

    public void setLocalDeliveryPrice(Double localDeliveryPrice) {
        this.localDeliveryPrice = localDeliveryPrice;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }
}
