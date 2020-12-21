package cc.lovesq.goodssnapshot.implv1;

import cc.lovesq.goodssnapshot.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GoodsServiceSnapshotStartup {

    private static Map<String, String> serviceTpl = new HashMap<>();

    static {
        // 服务信息应该写成枚举么？为什么？
        serviceTpl.put("express", "支持快递发货，本商品$info");
        serviceTpl.put("selfetch", "可就近选择自提点并预约自提时间");
        serviceTpl.put("refundAndReturn", "支持买家申请退换");
        serviceTpl.put("secureService", "该店铺交易由有赞提供资金存管服务，当符合以下条件时，资金自动结算给商家：买家确认收货或到达约定的自动确认收货日期。交易资金未经有赞存管的情形（储值型、电子卡券等）不在本服务范围内。");
        serviceTpl.put("subShop", "该店铺拥有线下门店，商家已展示门店信息");
    }

    /**
     * 问题在哪里？
     * 1. getServiceDesc 里的代码有太多的 if-else 语句；
     * 2. 如果有新增的 service ，是不是要改 getServiceDesc 里的代码 ？
     * 3. 如何实现动态加载新的 service 呢 ？
     *
     */
    public String getServiceDesc(Order order, String key) {

        if (key.equals("express")) {
            String tpl = serviceTpl.get("express");
            if (Objects.equals(order.getPrice(), 0)) {
                return tpl.replace("$info", "免运费");
            }
            else {
                return tpl.replace("$info", "￥" + order.getPrice() + "元");
            }
        }

        if (key.equals("selfetch")) {
            return serviceTpl.get("selfetch");
        }

        if (key.equals("refundAndReturn")) {
            return serviceTpl.get("refundAndReturn");
        }

        if (key.equals("secureService")) {
            String tpl = serviceTpl.get("secureService");
            return order.isSecuredOrder() ? tpl : "";
        }

        return "";
    }

    public String getServiceDesc2(Order order, String key) {

        if (!serviceTpl.containsKey(key)) {
            return "";
        }

        String tpl = serviceTpl.get(key);

        if (key.equals("express")) {
            if (Objects.equals(order.getPrice(), 0)) {
                return tpl.replace("$info", "免运费");
            }
            else {
                return tpl.replace("$info", "￥" + order.getPrice() + "元");
            }
        }

        if (key.equals("secureService")) {
            return order.isSecuredOrder() ? tpl : "";
        }

        return tpl;
    }
}


