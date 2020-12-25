package cc.lovesq.model;

public class DeliveryModel {

    /** 快递运费 */
    Long expressFee;

    /** 同城送起送金额 */
    Long localDeliveryBasePrice;

    /** 同城送配送金额 */
    Long localDeliveryPrice;

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
}
