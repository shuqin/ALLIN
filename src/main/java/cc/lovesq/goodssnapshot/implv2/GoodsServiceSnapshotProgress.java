package cc.lovesq.goodssnapshot.implv2;

import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.goodssnapshot.Order;

import java.util.*;
import java.util.stream.Collectors;

public class GoodsServiceSnapshotProgress {

    private static Map<String, ServiceTplEnum> serviceTplMap = new HashMap<>();

    static {
        for (ServiceTplEnum serviceTpl: ServiceTplEnum.values()) {
            serviceTplMap.put(serviceTpl.getKey(), serviceTpl);
        }
    }

    public List<GoodsServiceSnapshot> getServiceDescs(Order order, List<String> keys) {

        List<GoodsServiceSnapshot> goodsServiceSnapshots = new ArrayList<>();

        for (String key: keys) {

            GoodsServiceSnapshot goodsServiceSnapshot = new GoodsServiceSnapshot();
            goodsServiceSnapshot.setKey(key);
            goodsServiceSnapshot.setTitle(serviceTplMap.get(key).getTitle());
            goodsServiceSnapshot.setDesc(getServiceDesc2(order, key));
            goodsServiceSnapshots.add(goodsServiceSnapshot);
        }
        return goodsServiceSnapshots;

    }

    public List<GoodsServiceSnapshot> getServiceDescs2(Order order, List<String> keys) {
        return keys.stream().map(key -> transfer(order, key)).collect(Collectors.toList());
    }

    public GoodsServiceSnapshot transfer(Order order, String key) {
        GoodsServiceSnapshot goodsServiceSnapshot = new GoodsServiceSnapshot();
        goodsServiceSnapshot.setKey(key);
        goodsServiceSnapshot.setTitle(serviceTplMap.get(key).getTitle());
        goodsServiceSnapshot.setDesc(getServiceDesc2(order, key));
        return goodsServiceSnapshot;

    }


    public String getServiceDesc2(Order order, String key) {

        if (!serviceTplMap.containsKey(key)) {
            return "";
        }

        String tpl = serviceTplMap.get(key).getTpl();

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


