package cc.lovesq.constants;

public enum DeliveryType {

    express("express", 0, "快递发货"),

    selfetch("selfetch", 1, "到店自提"),

    localDelivery("localDelivery", 2, "同城配送"),

    ;

    private String deliveryType;

    private Integer code;

    private String desc;

    DeliveryType(String deliveryType, Integer code, String desc) {
        this.deliveryType = deliveryType;
        this.code = code;
        this.desc = desc;
    }

    public static DeliveryType getDeliveryType(Integer code) {
        for (DeliveryType deliveryType: DeliveryType.values()) {
            if (deliveryType.code.equals(code)) {
                return deliveryType;
            }
        }
        return null;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public Integer getCode() {
        return code;
    }
}
