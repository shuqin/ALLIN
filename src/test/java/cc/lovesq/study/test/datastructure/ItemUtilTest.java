package cc.lovesq.study.test.datastructure;

import cc.lovesq.CommonForTest;
import org.junit.Test;
import shared.utils.JsonPathUtils;
import zzz.study.utils.ItemUtil;
import zzz.study.utils.NewMapUtil;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by shuqin on 17/11/10.
 */
public class ItemUtilTest extends CommonForTest {

    String newItemInfoStr = "{\n"
            + "    \"item:s_id:18006666\": \"1024\",\n"
            + "    \"item:s_id:18008888\": \"1024\",\n"
            + "    \"item:g_id:18006666\": \"6666\",\n"
            + "    \"item:g_id:18008888\": \"8888\",\n"
            + "    \"item:num:18008888\": \"8\",\n"
            + "    \"item:num:18006666\": \"6\",\n"
            + "    \"item:item_core_id:18006666\": \"9876666\",\n"
            + "    \"item:item_core_id:18008888\": \"9878888\",\n"
            + "    \"item:order_no:18006666\": \"E20171013174712025\",\n"
            + "    \"item:order_no:18008888\": \"E20171013174712025\",\n"
            + "    \"item:id:18008888\": \"18008888\",\n"
            + "    \"item:id:18006666\": \"18006666\",\n"
            + "    \"item_core:num:9878888\": \"8\",\n"
            + "    \"item_core:num:9876666\": \"6\",\n"
            + "    \"item_core:id:9876666\": \"9876666\",\n"
            + "    \"item_core:id:9878888\": \"9878888\",\n"
            + "    \"item_price:item_id:1000\": \"9876666\",\n"
            + "    \"item_price:item_id:2000\": \"9878888\",\n"
            + "    \"item_price:price:1000\": \"100\",\n"
            + "    \"item_price:price:2000\": \"200\",\n"
            + "    \"item_price:id:2000\": \"2000\",\n"
            + "    \"item_price:id:1000\": \"1000\",\n"
            + "    \"item_price_change_log:id:1111\": \"1111\",\n"
            + "    \"item_price_change_log:id:2222\": \"2222\",\n"
            + "    \"item_price_change_log:item_id:1111\": \"9876666\",\n"
            + "    \"item_price_change_log:item_id:2222\": \"9878888\",\n"
            + "    \"item_price_change_log:detail:1111\": \"haha1111\",\n"
            + "    \"item_price_change_log:detail:2222\": \"haha2222\",\n"
            + "    \"item_price_change_log:id:3333\": \"3333\",\n"
            + "    \"item_price_change_log:id:4444\": \"4444\",\n"
            + "    \"item_price_change_log:item_id:3333\": \"9876666\",\n"
            + "    \"item_price_change_log:item_id:4444\": \"9878888\",\n"
            + "    \"item_price_change_log:detail:3333\": \"haha3333\",\n"
            + "    \"item_price_change_log:detail:4444\": \"haha4444\"\n"
            + "}";

    @Test
    public void testBuildItemIndexMapForNew() {
        Map<String, Object> itemInfoMap = JsonPathUtils.readMap(newItemInfoStr);
        Map<String, Map<String, String>> itemIndexMap = ItemUtil
                .buildItemIndexMap(NewMapUtil.transMap(itemInfoMap));

        System.out.println(itemIndexMap);

        eq("18006666", itemIndexMap.get("item:18006666").get("item:id"));
        eq("6666", itemIndexMap.get("item:18006666").get("item:g_id"));
        eq("1024", itemIndexMap.get("item:18006666").get("item:s_id"));
        eq("E20171013174712025", itemIndexMap.get("item:18006666").get("item:order_no"));
        eq("9876666", itemIndexMap.get("item:18006666").get("item:item_core_id"));

        eq("18008888", itemIndexMap.get("item:18008888").get("item:id"));
        eq("8888", itemIndexMap.get("item:18008888").get("item:g_id"));
        eq("1024", itemIndexMap.get("item:18008888").get("item:s_id"));
        eq("E20171013174712025", itemIndexMap.get("item:18008888").get("item:order_no"));
        eq("9878888", itemIndexMap.get("item:18008888").get("item:item_core_id"));

        eq("9876666", itemIndexMap.get("item_core:9876666").get("item_core:id"));
        eq("6", itemIndexMap.get("item_core:9876666").get("item_core:num"));

        eq("9878888", itemIndexMap.get("item_core:9878888").get("item_core:id"));
        eq("8", itemIndexMap.get("item_core:9878888").get("item_core:num"));

        eq("9876666", itemIndexMap.get("item_price:1000").get("item_price:item_id"));
        eq("1000", itemIndexMap.get("item_price:1000").get("item_price:id"));
        eq("100", itemIndexMap.get("item_price:1000").get("item_price:price"));

        eq("9878888", itemIndexMap.get("item_price:2000").get("item_price:item_id"));
        eq("2000", itemIndexMap.get("item_price:2000").get("item_price:id"));
        eq("200", itemIndexMap.get("item_price:2000").get("item_price:price"));

        eq("9876666", itemIndexMap.get("item_price_change_log:1111").get("item_price_change_log:item_id"));
        eq("haha1111", itemIndexMap.get("item_price_change_log:1111").get("item_price_change_log:detail"));
        eq("9878888", itemIndexMap.get("item_price_change_log:2222").get("item_price_change_log:item_id"));
        eq("haha2222", itemIndexMap.get("item_price_change_log:2222").get("item_price_change_log:detail"));

        eq("9876666", itemIndexMap.get("item_price_change_log:3333").get("item_price_change_log:item_id"));
        eq("haha3333", itemIndexMap.get("item_price_change_log:3333").get("item_price_change_log:detail"));
        eq("9878888", itemIndexMap.get("item_price_change_log:4444").get("item_price_change_log:item_id"));
        eq("haha4444", itemIndexMap.get("item_price_change_log:4444").get("item_price_change_log:detail"));
    }

    @Test
    public void testBuildFinalOrderItemMapForNew() {
        Map<String, Object> itemInfoMap = JsonPathUtils.readMap(newItemInfoStr);
        Map<String, Map<String, Object>> finalOrderItemMap = ItemUtil
                .buildFinalOrderItemMap(NewMapUtil.transMap(itemInfoMap));
        System.out.println(finalOrderItemMap);

        eq("18006666", finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item:id"));
        eq("6666", finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item:g_id"));
        eq("1024", finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item:s_id"));
        eq("E20171013174712025", finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item:order_no"));
        eq("9876666", finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item_core:id"));
        eq("6", finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item_core:num"));
        eq("9876666", finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item_price:item_id"));
        eq("100", finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item_price:price"));

        eq("18008888", finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item:id"));
        eq("8888", finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item:g_id"));
        eq("1024", finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item:s_id"));
        eq("E20171013174712025", finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item:order_no"));
        eq("9878888", finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item_core:id"));
        eq("8", finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item_core:num"));
        eq("9878888", finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item_price:item_id"));
        eq("200", finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item_price:price"));

        eq(Arrays.asList("haha3333", "haha1111"), finalOrderItemMap.get("1024_E20171013174712025_18006666").get("item_price_change_log:detail"));
        eq(Arrays.asList("haha2222", "haha4444"), finalOrderItemMap.get("1024_E20171013174712025_18008888").get("item_price_change_log:detail"));
    }

}
