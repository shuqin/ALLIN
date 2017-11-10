package zzz.study.utils;

/**
 * Created by shuqin on 17/11/10.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static zzz.study.utils.NewMapUtil.merge;

/**
 * Created by shuqin on 17/10/23.
 */
public class ItemUtil {

  private static Logger logger = LoggerFactory.getLogger(ItemUtil.class);

  /**
   * 构建一个订单的所有商品级别的信息以及映射
   * @param multiItemInfoMapForOneOrder 一个订单下多个商品的信息
   * @return 一个订单的所有商品级别的信息以及映射
   *
   * key 是 sID + order_no + item_id ; item_id 对应 item.id, 对应 item_core.item_core_id
   */
  public static Map<String, Map<String,Object>> buildFinalOrderItemMap(Map<String,String> multiItemInfoMapForOneOrder) {
    try {
      return mergeOrderItemMap(buildItemIndexMap(multiItemInfoMapForOneOrder));
    } catch (Exception ex) {
      logger.error("failed to buildFinalOrderItemMap for: " + multiItemInfoMapForOneOrder, ex);
      return new HashMap<>();
    }

  }

  /**
   * 构建一个订单的所有商品级别的信息以及映射
   * @param itemInfoMap 商品级别的信息
   * @return 一个订单的所有商品的信息
   *
   * NOTE: itemInfoMap 是对应一个订单的所有商品的信息的映射，不要传多个订单的信息进来，可能重复
   *
   * key 是 hbase table + ":" + table_id
   */
  public static Map<String,Map<String,String>> buildItemIndexMap(Map<String, String> itemInfoMap) {
    Map<String, Map<String,String>> itemIndexMap = new HashMap<>();

    Set<Map.Entry<String, String>> entries = itemInfoMap.entrySet();
    for (Map.Entry<String,String> entry: entries) {
      String key = entry.getKey();
      String value = String.valueOf(entry.getValue());
      String[] keyparts = key.split(":");

      // 只考虑三段式 tablename:field:id
      if (keyparts.length == 3) {
        String table = keyparts[0];
        String field = keyparts[1];
        String index = keyparts[2];

        String indexKey = table+ ":" + index;
        String fieldKey = table+":"+field;
        if (itemIndexMap.get(indexKey) == null) {
          itemIndexMap.put(indexKey, new HashMap<>());
        }
        itemIndexMap.get(indexKey).put(fieldKey, value);
      }
    }
    return itemIndexMap;
  }

  /**
   * 聚合一个订单下的所有商品信息
   * @param itemIndexMap 一个订单所有商品的信息映射
   * @return 一个订单下的所有商品信息
   *
   * key 是 sID + order_no + item_id ; item_id 对应 item.id, item_core.item_core_id
   */
  private static Map<String, Map<String,Object>> mergeOrderItemMap(Map<String, Map<String,String>> itemIndexMap) {

    if (itemIndexMap == null || itemIndexMap.isEmpty()) {
      return new HashMap<>();
    }

    // Map[oldItemId, newItemId]
    Map<String,String> old2newItemIdMap = new HashMap<>();
    Map<String,String> new2oldItemIdMap = new HashMap<>();

    Set<Map.Entry<String,Map<String,String>>> entries = itemIndexMap.entrySet();
    String orderNo = "";
    String sID = "";
    for (Map.Entry<String,Map<String,String>> entry: entries) {
      String indexKey = entry.getKey();
      Map<String,String> value = entry.getValue();

      if (indexKey.startsWith("item:")) {
        old2newItemIdMap.put(indexKey, value.get("item:item_core_id"));
        new2oldItemIdMap.put(value.get("item:item_core_id"), indexKey);
        orderNo = value.get("item:order_no");
        sID = value.get("item:s_id");
      }
    }

    Map<String, Map<String,Object>> newItemIndexMap = new HashMap<>();

    for (Map.Entry<String,Map<String,String>> entry: entries) {
      String indexKey = entry.getKey();
      Map<String,String> value = entry.getValue();

      if (indexKey.startsWith("item:")) {
        if (newItemIndexMap.get(indexKey) == null) {
          newItemIndexMap.put(indexKey, new HashMap<>());
        }
        newItemIndexMap.get(indexKey).putAll(value);
      }
      else if (indexKey.startsWith("item_core:")) {
        String itemCoreId = indexKey.split(":")[1];
        String oldItemId = new2oldItemIdMap.get(itemCoreId);
        if (newItemIndexMap.get(oldItemId) == null) {
          newItemIndexMap.put(oldItemId, new HashMap<>());
        }
        newItemIndexMap.get(oldItemId).putAll(value);
      }
      else if (indexKey.startsWith("item_price:")) {
        // item_price 与 item_id 一对一关系
        String itemCoreId = itemIndexMap.get(indexKey).get("item_price:item_id");
        String oldItemId = new2oldItemIdMap.get(itemCoreId);
        if (newItemIndexMap.get(oldItemId) == null) {
          newItemIndexMap.put(oldItemId, new HashMap<>());
        }
        newItemIndexMap.get(oldItemId).putAll(value);
      }
      else if (indexKey.startsWith("item_price_change_log:")) {
        // item_price_change_log 与 item_id 多对一关系
        String itemCoreId = itemIndexMap.get(indexKey).get("item_price_change_log:item_id");
        String oldItemId = new2oldItemIdMap.get(itemCoreId);
        if (newItemIndexMap.get(oldItemId) == null) {
          newItemIndexMap.put(oldItemId, new HashMap<>());
        }
        Map<String,Object> srcMap = newItemIndexMap.get(oldItemId);
        newItemIndexMap.get(oldItemId).putAll(merge(srcMap, value));
      }

    }

    return buildFinalOrderItemMap(newItemIndexMap, old2newItemIdMap, orderNo, sID);

  }


  /**
   * 构建最终的订单商品信息
   * @param itemIndexMap 商品信息
   * @param old2newItemIdMap 新老itemId映射
   * @param orderNo 订单号
   * @param sID 店铺号
   * @return 订单商品扩展信息
   */
  private static Map<String, Map<String,Object>> buildFinalOrderItemMap(Map<String, Map<String,Object>> itemIndexMap,
                                                                        Map<String,String> old2newItemIdMap,
                                                                        String orderNo, String sID) {
    Map<String, Map<String,Object>> finalResult = new HashMap<>();

    Set<Map.Entry<String,Map<String,Object>>> entries = itemIndexMap.entrySet();

    for (Map.Entry<String,Map<String,Object>> entry: entries) {
      String indexKey = entry.getKey();
      Map<String,Object> value = entry.getValue();

      String itemId = indexKey.split(":")[1];

      String itemKey = sID + "_" + orderNo + "_" + itemId;
      finalResult.put(itemKey, value);
    }
    return finalResult;
  }

}

