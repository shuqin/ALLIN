package zzz.study.patterns.collector;

import lombok.Data;

/**
 * 订单报表对象
 */
@Data
public class DefaultReportOrderInfo {

    private String orderNo;

    private OrderInfo orderInfo;
    private OrderItemInfo orderItemInfo;
}
