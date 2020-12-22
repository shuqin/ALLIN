package cc.lovesq.goodssnapshot.impl4;

import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.goodssnapshot.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GoodsServiceSnapshotProgress4 {

    @Resource
    private ServiceTplList4 serviceTplList4;

    public List<GoodsServiceSnapshot> getServiceDescs(Order order, List<String> keys) {

        List<GoodsServiceSnapshot> goodsServiceSnapshots = new ArrayList<>();

        for (String key: keys) {

            GoodsServiceSnapshot goodsServiceSnapshot = new GoodsServiceSnapshot();
            goodsServiceSnapshot.setKey(key);
            goodsServiceSnapshot.setTitle(serviceTplList4.getTpl(key, order.getBookTime()).getTitle());
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
        goodsServiceSnapshot.setTitle(serviceTplList4.getTpl(key, order.getBookTime()).getTitle());
        goodsServiceSnapshot.setDesc(getServiceDesc2(order, key));
        return goodsServiceSnapshot;

    }


    public String getServiceDesc2(Order order, String key) {

        if (!serviceTplList4.containsKey(key)) {
            return "";
        }

        String tpl = serviceTplList4.getTpl(key, order.getBookTime()).getTpl();

        if (key.equals("express")) {
            if (Objects.equals(order.getPrice(), 0.0)) {
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


