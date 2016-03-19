package shared.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import shared.utils.JsonExtractUtil;

import java.util.HashMap;
import java.util.Map;

public class JsonExtractUtilTest {

    String json = "{\"IS_MEMBER\":\"true\",\"PRICE\":{\"originAmount\":4990,\"totalAmount\":4990},\"BIZ_ORDER_ATTRIBUTE\":\"{\\\"CART_INFO\\\":\\\"fromRetail\\\"}\", \"BIZ_ITEM_ATTRIBUTE\":\"{\\\"IS_OUTSIDE\\\":\\\"true\\\"}\"}";

    @Test
    public void testExtract() {
        Assert.assertEquals("", JsonExtractUtil.extractString(null, "IS_MEMBER"));
        Assert.assertEquals("", JsonExtractUtil.extractString(json, null));
        Assert.assertEquals("true", JsonExtractUtil.extractString(json, "IS_MEMBER"));
        Assert.assertEquals("4990", JsonExtractUtil.extractString(json, "PRICE", "totalAmount", false));
        Assert.assertEquals("fromRetail", JsonExtractUtil.extractString(json, "BIZ_ORDER_ATTRIBUTE", "CART_INFO", true));
        Assert.assertEquals("fromRetail", JsonExtractUtil.extractString(json, "BIZ_ORDER_ATTRIBUTE", "CART_INFO"));
    }

    @Test
    public void testExtractFromMap() {
        Assert.assertEquals(null, JsonExtractUtil.extract((Map) null, "abc", "def"));
        Assert.assertEquals(null, JsonExtractUtil.extract(new HashMap(), null, null));

        Map<String, Object> map = JSONObject.parseObject(json);
        Assert.assertEquals("true", JsonExtractUtil.extract(map, "IS_MEMBER", null));
        Assert.assertEquals(4990, JsonExtractUtil.extract(map, "PRICE", "totalAmount"));
        Assert.assertEquals("fromRetail", JsonExtractUtil.extract(map, "BIZ_ORDER_ATTRIBUTE", "CART_INFO"));
    }

    @Test
    public void testExtractOrderBizAttributeString() {
        Assert.assertEquals("fromRetail", JsonExtractUtil.extractOrderBizAttributeString(json, "CART_INFO"));
    }

    @Test
    public void testExtractItemBizAttributeString() {
        Assert.assertEquals("true", JsonExtractUtil.extractItemBizAttributeString(json, "IS_OUTSIDE"));
    }

    @Test
    public void testPerformance() {
        for (int i = 1; i <= 1000; i = i * 10) {
            double avg = 0.0;
            double total = 0;
            long num = 100 * i;
            for (int j = 0; j < num; j++) {
                long start = System.nanoTime() / 1000;
                //Assert.assertEquals("true", JsonUtils.extractString(json, "IS_MEMBER"));
                Assert.assertEquals("4990", JsonExtractUtil.extractString(json, "PRICE", "totalAmount", false));
                long endTime = System.nanoTime() / 1000;
                long cost = (endTime - start);
                //System.out.println("cost: " + cost);
                total += cost;
            }
            System.out.println("avg: " + total / num);
        }



    }


}


