package cc.lovesq.goodssnapshot;

import java.util.List;
import java.util.Objects;

public class Order {

    /** 订单号 */
    private String orderNo;

    /** 订单类型 0 普通 */
    private Integer orderType;

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
}
