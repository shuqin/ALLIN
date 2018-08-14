package zzz.study.algorithm.object;

import com.alibaba.fastjson.JSON;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import zzz.study.datastructure.map.TransferUtil;

public class MapToObject {

  private static final String json = "{\n"
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
             + "    \n"
             + "    \"item_core:num:9878888\": \"8\",\n"
             + "    \"item_core:num:9876666\": \"6\",\n"
             + "    \"item_core:id:9876666\": \"9876666\",\n"
             + "    \"item_core:id:9878888\": \"9878888\",\n"
             + "\n"
             + "    \"item_price:item_id:1000\": \"9876666\",\n"
             + "    \"item_price:item_id:2000\": \"9878888\",\n"
             + "    \"item_price:price:1000\": \"100\",\n"
             + "    \"item_price:price:2000\": \"200\",\n"
             + "    \"item_price:id:2000\": \"2000\",\n"
             + "    \"item_price:id:1000\": \"1000\",\n"
             + "\n"
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


  public static void main(String[] args) {
    Order order = transferOrder(json);
    System.out.println(JSON.toJSONString(order));
  }

  public static Order transferOrder(String json) {
    Map<String, Map<String,Object>> groupedMaps = group(json);
    Map<String, Map<String,Object>> groupedMapsCamel = new HashMap<>();
    Set<String> ignoreSets = new HashSet();
    groupedMaps.forEach(
        (key, mapForKey) -> {
          Map<String,Object> keytoCamel = TransferUtil.generalMapProcess(mapForKey, TransferUtil::underlineToCamel, ignoreSets);
          groupedMapsCamel.put(key, keytoCamel);
        }
    );
    return relate(groupedMapsCamel);
  }

  /**
   * 转换成 Map[tablename:id => Map["field": value]]
   */
  public static Map<String, Map<String,Object>> group(String json) {
    Map<String, Object> map = JSON.parseObject(json);
    Map<String, Map<String,Object>> groupedMaps = new HashMap();
    map.forEach(
        (keyInJson, value) -> {
          TableField tableField = TableField.buildFrom(keyInJson);
          String key = tableField.getTablename() + ":" + tableField.getId();
          Map<String,Object> mapForKey = groupedMaps.getOrDefault(key, new HashMap<>());
          mapForKey.put(tableField.getField(), value);
          groupedMaps.put(key, mapForKey);
        }
    );
    return groupedMaps;
  }

  /**
   * 将分组后的子map先转成相应单个对象，再按照某个key值进行关联
   */
  public static Order relate(Map<String, Map<String,Object>> groupedMaps) {
    List<Item> items = new ArrayList<>();
    List<ItemCore> itemCores = new ArrayList<>();
    List<ItemPrice> itemPrices = new ArrayList<>();
    List<ItemPriceChangeLog> itemPriceChangeLogs = new ArrayList<>();
    groupedMaps.forEach(
        (key, mapForKey) -> {
          if (key.startsWith("item:")) {
            items.add(map2Bean(mapForKey, Item.class));
          }
          else if (key.startsWith("item_core:")) {
            itemCores.add(map2Bean(mapForKey, ItemCore.class));
          }
          else if (key.startsWith("item_price:")) {
            itemPrices.add(map2Bean(mapForKey, ItemPrice.class));
          }
          else if (key.startsWith("item_price_change_log:")) {
            itemPriceChangeLogs.add(map2Bean(mapForKey, ItemPriceChangeLog.class));
          }
        }
    );

    Map<String ,List<Item>> itemMap = items.stream().collect(Collectors.groupingBy(
        Item::getItemCoreId
    ));
    Map<String ,List<ItemPrice>> itemPriceMap = itemPrices.stream().collect(Collectors.groupingBy(
        ItemPrice::getItemId
    ));
    Map<String ,List<ItemPriceChangeLog>> itemPriceChangeLogMap = itemPriceChangeLogs.stream().collect(Collectors.groupingBy(
        ItemPriceChangeLog::getItemId
    ));
    itemCores.forEach(
        itemCore -> {
          String itemId = itemCore.getId();
          itemCore.setItem(itemMap.get(itemId).get(0));
          itemCore.setItemPrice(itemPriceMap.get(itemId).get(0));
          itemCore.setItemPriceChangeLogs(itemPriceChangeLogMap.get(itemId));
        }
    );
    Order order = new Order();
    order.setItemCores(itemCores);
    return order;
  }

  public static <T> T map2Bean(Map map, Class<T> c) {
    try {
      T t = c.newInstance();
      BeanUtils.populate(t, map);
      return t;
    } catch (Exception ex) {
      throw new RuntimeException(ex.getCause());
    }
  }

}
