package cc.lovesq.goodssnapshot.impl4;

import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.model.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GoodsServiceSnapshotProgress {

    @Resource
    private ServiceTplSplitList serviceTplSplitList;

    @Resource
    private ServiceTplList serviceTplList;

    public List<GoodsServiceSnapshot> getServiceDescs(Order order, List<String> keys) {
        return keys.stream().map(key -> transfer(order, key)).filter(gss -> gss.getDesc().length() > 0).collect(Collectors.toList());
    }

    public GoodsServiceSnapshot transfer(Order order, String key) {
        GoodsServiceSnapshot goodsServiceSnapshot = new GoodsServiceSnapshot();
        goodsServiceSnapshot.setKey(key);
        goodsServiceSnapshot.setTitle(getServiceTplListInf().getTpl(key, order.getBookTime()).getTitle());
        goodsServiceSnapshot.setDesc(getServiceDescInner(order, key));
        return goodsServiceSnapshot;

    }


    public String getServiceDescInner(Order order, String key) {

        if (!getServiceTplListInf().containsKey(key)) {
            return "";
        }

        String tpl = getServiceTplListInf().getTpl(key, order.getBookTime()).getTpl();

        if (key.equals("express")) {
            if (Objects.equals(order.getPrice(), 0.0)) {
                return tpl.replace("$info", "免运费");
            }
            else {
                return tpl.replace("$info", "运费￥" + order.getPrice() + "元");
            }
        }

        if (key.equals("localDelivery")) {
            return tpl.replace("$localDeliveryBasePrice", order.getLocalDeliveryBasePrice().toString())
                      .replace("$localDeliveryPrice", order.getLocalDeliveryPrice().toString());
        }

        if (key.equals("codpay")) {
            return order.isCodPay() ? tpl : "";
        }

        if (key.equals("secureService")) {
            return order.isSecuredOrder() ? tpl :"";
        }

        if (key.equals("drectoseller")) {
            return !order.isSecuredOrder() ? tpl : "";
        }

        return tpl;
    }

    public ServiceTplListInf getServiceTplListInf() {
        return serviceTplList;
    }

}


