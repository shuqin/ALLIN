package zzz.study.algorithm.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static shared.utils.BeanUtil.map2Bean;

public class ObjectMapping {

  Map<String, BizObjects> objMapping;

  public ObjectMapping() {
    objMapping = new HashMap<>();
    objMapping.put("item", new BizObjects<Item,String>(Item.class, new HashMap<>(), Item::getItemCoreId));
    objMapping.put("item_core", new BizObjects<ItemCore,String>(ItemCore.class, new HashMap<>(), ItemCore::getId));
    objMapping.put("item_price", new BizObjects<ItemPrice,String>(ItemPrice.class, new HashMap<>(), ItemPrice::getItemId));
    objMapping.put("item_price_change_log", new BizObjects<ItemPriceChangeLog,String>(ItemPriceChangeLog.class, new HashMap<>(), ItemPriceChangeLog::getItemId));
  }

  public ObjectMapping FillFrom(Map<String, Map<String,Object>> groupedMaps) {
    groupedMaps.forEach(
        (key, mapForKey) -> {
          String prefixOfKey = key.split(":")[0];
          BizObjects bizObjects = objMapping.get(prefixOfKey);
          bizObjects.add(map2Bean(mapForKey, bizObjects.getObjectClass()));
        }
    );
    return this;
  }

  public List<ItemCore> buildFinalList() {
    Map<String, List<ItemCore>> itemCores = objMapping.get("item_core").getObjects();

    List<ItemCore> finalItemCoreList = new ArrayList<>();
    itemCores.forEach(
        (itemCoreId, itemCoreList) -> {
          ItemCore itemCore = itemCoreList.get(0);
          itemCore.setItem((Item) objMapping.get("item").getSingle(itemCoreId));
          itemCore.setItemPrice((ItemPrice) objMapping.get("item_price").getSingle(itemCoreId));
          itemCore.setItemPriceChangeLogs(objMapping.get("item_price_change_log").get(itemCoreId));
          finalItemCoreList.add(itemCore);
        }
    );
    return finalItemCoreList;
  }

}
