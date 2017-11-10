package zzz.study.utils;

/**
 * Created by shuqin on 17/11/10.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

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
   * key 是 sID + order_no + item_id ; item_id 对应 item.id
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
   * key = table:table_unique_id , value = map [ table:field, value ]
   *
   * eg. map [ item:goods_type:1800888 = 0 , item:num:1800888 = 8 ]
   *     will be transformed into map[item:1800888 = map[item:goods_type=0 , item:num=8]]
   */
  public static Map<String,Map<String,String>> buildItemIndexMap(Map<String, String> itemInfoMap) {
    Map<String, Map<String,String>> itemIndexMap = new HashMap<>();

    itemInfoMap.forEach(
        (key, value) -> {
          String[] keyparts = key.split(":");

          // 只考虑三段式 tablename:field:id
          if (keyparts != null && keyparts.length == 3) {
            String table = keyparts[0];
            String field = keyparts[1];
            String index = keyparts[2];

            String indexKey = table+ ":" + index;
            String fieldKey = table+":"+field;
            if (itemIndexMap.get(indexKey) == null) {
              itemIndexMap.put(indexKey, new HashMap<>());
            }
            itemIndexMap.get(indexKey).put(fieldKey, String.valueOf(value));
          }
        }
    );

    return itemIndexMap;
  }

  // 这个配置含有新老itemId映射的信息以及获取orderNo, sID 的字段信息
  private static List<String>
      itemBaseDataConf = Arrays.asList("item:", "item:item_core_id",
                                       "item:order_no", "item:s_id");
  private static List<String> itemCoreDataConf = Arrays.asList("item_core:");
  private static List<String> itemPriceDataConf = Arrays.asList("item_price:", "item_price:item_id");
  private static List<String> itemPriceChangelogDataConf = Arrays.asList("item_price_change_log:", "item_price_change_log:item_id");

  /**
   * 聚合一个订单下的所有商品信息
   * @param itemIndexMap 一个订单所有商品的信息映射
   * @return 一个订单下的所有商品信息
   *
   * key 是 sID + order_no + item_id ;
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
    String kdtId = "";

    // 构建itemID映射
    for (Map.Entry<String,Map<String,String>> entry: entries) {
      String indexKey = entry.getKey();
      Map<String,String> value = entry.getValue();

      if (indexKey.startsWith(itemBaseDataConf.get(0))) {
        old2newItemIdMap.put(indexKey, value.get(itemBaseDataConf.get(1)));
        new2oldItemIdMap.put(value.get(itemBaseDataConf.get(1)), indexKey);
        orderNo = value.get(itemBaseDataConf.get(2));
        kdtId = value.get(itemBaseDataConf.get(3));
      }
    }

    Map<String, Map<String,Object>> newItemIndexMap = buildNewIndexMapAll(itemIndexMap, new2oldItemIdMap);
    return buildFinalOrderItemMap(newItemIndexMap, old2newItemIdMap, orderNo, kdtId);

  }

  private static Map<String, Map<String,Object>> buildNewIndexMapAll(Map<String, Map<String,String>> itemIndexMap, Map<String,String> new2oldItemIdMap) {

    Map<String, Map<String,Object>> newItemIndexMap = new HashMap<>();

    itemIndexMap.forEach(
        (indexKey, value) -> {

          if (indexKey.startsWith(itemBaseDataConf.get(0))) {
            putNewIndexMap(newItemIndexMap, indexKey, value, key -> key);
          }
          else if (indexKey.startsWith(itemCoreDataConf.get(0))) {
            putNewIndexMap(newItemIndexMap, indexKey, value,
                           key -> new2oldItemIdMap.get(key.split(":")[1]));
          }
          else if (indexKey.startsWith(itemPriceDataConf.get(0))) {
            // 与 item_id 一对一关系的表的扩展信息
            putNewIndexMap(newItemIndexMap, indexKey, value,
                           key -> {
                             String itemCoreId = itemIndexMap.get(indexKey).get(itemPriceDataConf.get(1));
                             return new2oldItemIdMap.get(itemCoreId);
                           } );
          }
          else if (indexKey.startsWith(itemPriceChangelogDataConf.get(0))) {
            //  与 item_id 多对一关系的表的扩展信息
            String tcOrderItemId = itemIndexMap.get(indexKey).get(itemPriceChangelogDataConf.get(1));
            String oldItemId = new2oldItemIdMap.get(tcOrderItemId);
            if (newItemIndexMap.get(oldItemId) == null) {
              newItemIndexMap.put(oldItemId, new HashMap<>());
            }
            Map<String,Object> srcMap = newItemIndexMap.get(oldItemId);
            newItemIndexMap.get(oldItemId).putAll(merge(srcMap, value));
          }
        }
    );

    return newItemIndexMap;

  }

  /*
   * 将各商品信息聚合到相应的原itemId的键下
   */
  private static void putNewIndexMap(Map<String, Map<String,Object>> newItemIndexMap,
                                     String indexKey, Map<String,String> value, Function<String, String> getOriginItemIdFunc) {
    String originItemId = getOriginItemIdFunc.apply(indexKey);
    if (newItemIndexMap.get(originItemId) == null) {
      newItemIndexMap.put(originItemId, new HashMap<>());
    }
    newItemIndexMap.get(originItemId).putAll(value);
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
    itemIndexMap.forEach(
        (indexKey,value) -> {
          String itemId = indexKey.split(":")[1];
          String itemKey = sID + "_" + orderNo + "_" + itemId;
          finalResult.put(itemKey, value);
        }
    );
    return finalResult;
  }

}

