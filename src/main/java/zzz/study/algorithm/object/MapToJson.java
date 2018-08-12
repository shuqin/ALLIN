package zzz.study.algorithm.object;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

public class MapToJson {

  public static void main(String[] args) {
    Order order = new Order();

    ItemPriceChangeLog itemPriceChangeLog1111 = new ItemPriceChangeLog();
    itemPriceChangeLog1111.setId("1111");
    itemPriceChangeLog1111.setItem_id("9876666");
    itemPriceChangeLog1111.setDetail("haha1111");

    ItemPriceChangeLog itemPriceChangeLog2222 = new ItemPriceChangeLog();
    itemPriceChangeLog2222.setId("2222");
    itemPriceChangeLog2222.setItem_id("9878888");
    itemPriceChangeLog2222.setDetail("haha2222");

    ItemPriceChangeLog itemPriceChangeLog3333 = new ItemPriceChangeLog();
    itemPriceChangeLog3333.setId("3333");
    itemPriceChangeLog3333.setItem_id("9876666");
    itemPriceChangeLog3333.setDetail("haha3333");

    ItemPriceChangeLog itemPriceChangeLog4444 = new ItemPriceChangeLog();
    itemPriceChangeLog4444.setId("4444");
    itemPriceChangeLog4444.setItem_id("9878888");
    itemPriceChangeLog4444.setDetail("haha4444");

    ItemPrice itemPrice1000 = new ItemPrice();
    itemPrice1000.setId("1000");
    itemPrice1000.setItem_id("9876666");
    itemPrice1000.setPrice("100");

    ItemPrice itemPrice2000 = new ItemPrice();
    itemPrice2000.setId("2000");
    itemPrice2000.setItem_id("9878888");
    itemPrice2000.setPrice("200");

    ItemCore itemCore9876666 = new ItemCore();
    itemCore9876666.setId("9876666");
    itemCore9876666.setNum("6");

    ItemCore itemCore9878888 = new ItemCore();
    itemCore9878888.setId("9878888");
    itemCore9878888.setNum("8");

    Item item18006666 = new Item();
    item18006666.setId("18006666");
    item18006666.setItem_core_id("9876666");
    item18006666.setS_id("1024");
    item18006666.setG_id("6666");
    item18006666.setNum("6");
    item18006666.setOrder_no("E20171013174712025");

    Item item18008888 = new Item();
    item18008888.setId("18008888");
    item18008888.setItem_core_id("9878888");
    item18008888.setS_id("1024");
    item18008888.setG_id("8888");
    item18008888.setNum("8");
    item18008888.setOrder_no("E20171013174712025");

    itemCore9876666.setItem(item18006666);
    itemCore9876666.setItemPrice(itemPrice1000);
    itemCore9876666.setItemPriceChangeLogs(Arrays.asList(itemPriceChangeLog1111, itemPriceChangeLog3333));

    itemCore9878888.setItem(item18008888);
    itemCore9878888.setItemPrice(itemPrice2000);
    itemCore9878888.setItemPriceChangeLogs(Arrays.asList(itemPriceChangeLog2222, itemPriceChangeLog4444));

    order.setItemCores(Arrays.asList(itemCore9876666, itemCore9878888));

    System.out.println(JSON.toJSONString(order));

    String json = "{\"itemCores\":[{\"id\":\"9876666\",\"item\":{\"g_id\":\"6666\",\"id\":\"18006666\",\"item_core_id\":\"9876666\",\"num\":\"6\",\"order_no\":\"E20171013174712025\",\"s_id\":\"1024\"},\"itemPrice\":{\"id\":\"1000\",\"item_id\":\"9876666\",\"price\":\"100\"},\"itemPriceChangeLogs\":[{\"detail\":\"haha1111\",\"id\":\"1111\",\"item_id\":\"9876666\"},{\"detail\":\"haha3333\",\"id\":\"3333\",\"item_id\":\"9876666\"}],\"num\":\"6\"},{\"id\":\"9878888\",\"item\":{\"g_id\":\"8888\",\"id\":\"18008888\",\"item_core_id\":\"9878888\",\"num\":\"8\",\"order_no\":\"E20171013174712025\",\"s_id\":\"1024\"},\"itemPrice\":{\"id\":\"2000\",\"item_id\":\"9878888\",\"price\":\"200\"},\"itemPriceChangeLogs\":[{\"detail\":\"haha2222\",\"id\":\"2222\",\"item_id\":\"9878888\"},{\"detail\":\"haha4444\",\"id\":\"4444\",\"item_id\":\"9878888\"}],\"num\":\"8\"}]}";

    Order orderFromjson = JSON.parseObject(json, Order.class);
    System.out.println(orderFromjson.equals(order));


  }

}
