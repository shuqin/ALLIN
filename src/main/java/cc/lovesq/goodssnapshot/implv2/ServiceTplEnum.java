package cc.lovesq.goodssnapshot.implv2;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public enum ServiceTplEnum {

    express("express", "快递发货", "支持快递发货，本商品$info"),

    selfetch("selfetch", "到店自提", "可就近选择自提点并预约自提时间"),

    refundAndReturn("refundAndReturn", "支持退换", "支持买家申请退换"),

    secureService("secureService","收货后结算", "该店铺交易由有赞提供资金存管服务，当符合以下条件时，资金自动结算给商家：买家确认收货或到达约定的自动确认收货日期。交易资金未经有赞存管的情形（储值型、电子卡券等）不在本服务范围内。"),

    subShop("subShop", "线下门店", "该店铺拥有线下门店，商家已展示门店信息"),

    ;

    private String key;
    private String title;
    private String tpl;

    ServiceTplEnum(String key, String title, String tpl) {
        this.key = key;
        this.title = title;
        this.tpl = tpl;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getTpl() {
        return tpl;
    }

    @Override
    public String toString() {
        return String.format("{\"key\": \"%s\", \"title\": \"%s\", \"tpl\":\"%s\"}", key, title, tpl);
    }

    static class ServiceTplTest {
        public static void main(String[] args) {
            List<String> jsons = Arrays.stream(ServiceTplEnum.values()).map(ServiceTplEnum::toString).collect(toList());
            String json = "[" + String.join(",", jsons) + "]";

            System.out.println(json);
        }
    }
}
