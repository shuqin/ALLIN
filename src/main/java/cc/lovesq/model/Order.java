package cc.lovesq.model;

import cc.lovesq.constants.DeliveryType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

    /** 订单号 */
    private String orderNo;

    /** 店铺ID */
    private Long shopId;

    /** 下单时间 */
    private Long bookTime;

    /** 下单人ID */
    private Long userId;

    /** 是否货到付款 */
    private Boolean isCodPay;

    /** 是否担保交易 */
    private Boolean isSecuredOrder;

    /** 是否有线下门店 */
    private Boolean hasRetailShop;

    /** 配送方式 0 快递 1 自提 2 同城送 */
    private DeliveryType deliveryType;

    /** 订单金额, 分为单位 */
    private Long price;

    /** 快递配送金额 */
    private Long expressFee;

    /** 同城配送起送金额 */
    private Long localDeliveryBasePrice;

    /** 同城配送金额 */
    private Long localDeliveryPrice;

    /** 订单的服务 keys */
    private List<String> keys;

    public OrderDO toOrderDO() {
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderNo(orderNo);
        orderDO.setShopId(shopId);
        orderDO.setBookTime(bookTime);
        orderDO.setUserId(userId);

        orderDO.setDeliveryType(deliveryType.getCode());
        orderDO.setPrice(price);
        orderDO.setExtend(formExtend());

        orderDO.setGmtCreate(new Date());
        orderDO.setGmtModified(new Date());

        return orderDO;
    }

    public static Order from(OrderDO orderDO) {
        Order order = new Order();
        order.setOrderNo(orderDO.getOrderNo());
        order.setShopId(orderDO.getShopId());
        order.setBookTime(orderDO.getBookTime());
        order.setUserId(orderDO.getUserId());
        order.setPrice(orderDO.getPrice());
        order.setDeliveryType(DeliveryType.getDeliveryType(orderDO.getDeliveryType()));

        String extend = orderDO.getExtend();
        JSONObject extMap = JSON.parseObject(extend);
        order.setIsCodPay(extMap.getBoolean("isCodPay"));
        order.setIsSecuredOrder(extMap.getBoolean("isSecuredOrder"));
        order.setHasRetailShop(extMap.getBoolean("hasRetailShop"));
        order.setExpressFee(extMap.getLong("expressFee"));
        order.setLocalDeliveryBasePrice(extMap.getLong("localDeliveryBasePrice"));
        order.setLocalDeliveryPrice(extMap.getLong("localDeliveryPrice"));
        order.setKeys(extMap.getJSONArray("keys").toJavaList(String.class));

        return order;
    }

    private String formExtend() {
        Map<String, Object> ext = new HashMap<>();
        ext.put("isCodPay", isCodPay);
        ext.put("isSecuredOrder", isSecuredOrder);
        ext.put("hasRetailShop", hasRetailShop);
        ext.put("expressFee", expressFee);
        ext.put("localDeliveryBasePrice", localDeliveryBasePrice);
        ext.put("localDeliveryPrice", localDeliveryPrice);
        ext.put("keys", keys);
        return JSON.toJSONString(ext);
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getBookTime() {
        return bookTime;
    }

    public void setBookTime(Long bookTime) {
        this.bookTime = bookTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean isCodPay() {
        return isCodPay;
    }

    public void setIsCodPay(Boolean codPay) {
        isCodPay = codPay;
    }

    public Boolean isSecuredOrder() {
        return isSecuredOrder;
    }

    public void setIsSecuredOrder(Boolean securedOrder) {
        isSecuredOrder = securedOrder;
    }

    public Boolean isHasRetailShop() {
        return hasRetailShop;
    }

    public void setHasRetailShop(Boolean hasRetailShop) {
        this.hasRetailShop = hasRetailShop;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(Long expressFee) {
        this.expressFee = expressFee;
    }

    public Long getLocalDeliveryBasePrice() {
        return localDeliveryBasePrice;
    }

    public void setLocalDeliveryBasePrice(Long localDeliveryBasePrice) {
        this.localDeliveryBasePrice = localDeliveryBasePrice;
    }

    public Long getLocalDeliveryPrice() {
        return localDeliveryPrice;
    }

    public void setLocalDeliveryPrice(Long localDeliveryPrice) {
        this.localDeliveryPrice = localDeliveryPrice;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }
}
