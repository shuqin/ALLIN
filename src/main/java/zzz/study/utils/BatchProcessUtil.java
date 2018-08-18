package zzz.study.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by shuqin on 17/1/19.
 */
public class BatchProcessUtil {

  public static String batchProcessOrders(Consumer<String> processFunction, List<String> orders) {
    StringBuilder result = new StringBuilder();
    for (String orderNo : orders) {
      String orderNoTrimed = orderNo.trim();
      try {
        processFunction.accept(orderNo);
        result.append(orderNoTrimed + " OK , 请稍后查看!\n");
      } catch (Exception e) {
        result.append(orderNoTrimed + " Failed, 请稍后重试!\n");
      }
    }
    return result.toString();
  }

  public static void sync(String orderNo) {
    System.out.println("sync order state " + orderNo);
  }

  public static void refund(String orderNo) {
    System.out.println("refund for " + orderNo);
  }

  public static void cancel(String orderNo) {
    System.out.println("cancel " + orderNo);
  }

  public static void main(String[] args) {
    List<String> orders = Arrays.asList(new String[]{"E001", "E002", "E003"});
    batchProcessOrders((orderNo) -> sync(orderNo), orders);
    batchProcessOrders((orderNo) -> refund(orderNo), orders);
    batchProcessOrders((orderNo) -> cancel(orderNo), orders);
  }
}
