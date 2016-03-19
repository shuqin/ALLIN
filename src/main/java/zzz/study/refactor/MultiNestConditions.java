package zzz.study.refactor;

import lombok.Data;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * TODO
 * Created by qinshu on 2021/7/8
 */
public class MultiNestConditions {

    public String format(OrderDetailResponse orderDetailResponse) {

        int refundState = orderDetailResponse.getRefundState();
        int orderState = orderDetailResponse.getOrderState();
        int orderType = orderDetailResponse.getOrderType();
        int expressType = orderDetailResponse.getExpressType();
        int bizType = orderDetailResponse.getBizType();

        String formatStateStr = "";
        if (refundState == OrderRefundState.NORMAL.key()) {
            if (orderState == OrderState.TOPAY.getState()) {
                // 是否是定金预售订单--代付尾款阶段
                if (isPresaleFinalPayment(orderDetailResponse)) {
                    formatStateStr = "定金已支付，等待买家支付尾款";
                } else {
                    //待付款
                    formatStateStr = "商品已拍下，等待买家付款";
                }
            } else if (orderState == OrderState.PAID.getState()) {
                if (orderType == OrderType.GROUP.getValue()) {
                    formatStateStr = "已成团，等待商家发货";
                } else if (orderType == OrderType.HOTEL.getValue()) {
                    formatStateStr = "买家已付款，等待商家接单";
                } else if (isStockDeductOverSale(orderDetailResponse.getTcExtra())) {
                    formatStateStr = "超卖订单，等待商家确认";
                } else if (isStockDeductDoing(orderDetailResponse.getTcExtra())) {
                    formatStateStr = "系统确认库存中";
                } else {
                    formatStateStr = "等待商家发货";
                }
            } else if (orderState == OrderState.SENT.getState()) {
                if (Objects.equals(bizType, BizCategoryEnum.HOTEL.getValue())) {
                    formatStateStr = "商家已接单，等待交易成功";
                } else if (isKnowledgeOrder(orderDetailResponse)) {
                    formatStateStr = "买家已付款，等待交易成功";
                } else if (BooleanUtils.isTrue(orderDetailResponse.isVirtualOrder())
                        || BooleanUtils.isTrue(orderDetailResponse.isVirtualTicket())) {
                    // 虚拟商品和电子卡券订单已支付会直接映射成已发货，需要处理超卖逻辑
                    if (isStockDeductOverSale(orderDetailResponse.getTcExtra())) {
                        formatStateStr = "超卖订单，等待商家确认";
                    } else if (isStockDeductDoing(orderDetailResponse.getTcExtra())) {
                        formatStateStr = "系统确认库存中";
                    }
                } else {
                    formatStateStr = "商家已发货，等待交易成功";
                }
            } else if (orderState == OrderState.CONFIRM.getState()) {
                if (expressType == ExpressType.LOCAL_DELIVERY.getKey()) {
                    formatStateStr = "等待商家接单";
                } else {
                    formatStateStr = "买家已付款，等待成团";
                }
            }
            if (expressType == ExpressType.SELF_FETCH.getKey() && orderState == OrderState.PAID.getState()) {
                formatStateStr = "商家已接单，等待买家提货";
            }
        } else {
            //退款订单
            if (refundState == OrderRefundState.PART.key()) {
                formatStateStr = "订单部分退款中，请尽快处理";
            } else if (refundState == OrderRefundState.FULL.key()) {
                formatStateStr = "订单全额退款中，请尽快处理";
            }
        }




        return formatStateStr;
    }

    private boolean isStockDeductDoing(Map tcExtra) {
        return false;
    }

    private boolean isKnowledgeOrder(OrderDetailResponse orderDetailResponse) {
        return false;
    }

    private boolean isPresaleFinalPayment(OrderDetailResponse orderDetailResponse) {
        return true;
    }

    private boolean isStockDeductOverSale(Map tcExtra) {
        return true;
    }

    public static String ifThen(List<PredicatorWrapper> conditions, String value) {
        boolean isAllMatched = conditions.stream().allMatch(PredicatorWrapper::test);
        return isAllMatched ? value : null;
    }

    public static String cascade(String... strs) {
        for (String s: strs) {
            if (StringUtils.isNotBlank(s)) {
                return s;
            }
        }
        return "";
    }

    public static void main(String[] args) {

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setOrderState(OrderState.TOPAY.getState());
        orderDetailResponse.setOrderType(OrderType.GROUP.getValue());
        orderDetailResponse.setRefundState(OrderRefundState.NORMAL.key());

        String value = ifThen(
            Arrays.asList(
                new PredicatorWrapper<>(OrderDetailResponse::noRefund, orderDetailResponse),
                new PredicatorWrapper<>(OrderDetailResponse::isToPay, orderDetailResponse),
                new PredicatorWrapper<>(OrderDetailResponse::isGroupOrder, orderDetailResponse)
            ), "已成团，等待商家发货"
        );
        System.out.println(value);

        orderDetailResponse.setOrderType(OrderType.HOTEL.getValue());
        String value2 = cascade(
            ifThen(
                    Arrays.asList(
                            new PredicatorWrapper<>(OrderDetailResponse::noRefund, orderDetailResponse),
                            new PredicatorWrapper<>(OrderDetailResponse::isToPay, orderDetailResponse),
                            new PredicatorWrapper<>(OrderDetailResponse::isGroupOrder, orderDetailResponse)
                    ), "已成团，等待商家发货"
            ),
            ifThen(
                    Arrays.asList(
                            new PredicatorWrapper<>(OrderDetailResponse::noRefund, orderDetailResponse),
                            new PredicatorWrapper<>(OrderDetailResponse::isToPay, orderDetailResponse),
                            new PredicatorWrapper<>(OrderDetailResponse::isHotelOrder, orderDetailResponse)
                    ), "买家已付款，等待商家接单"
            )
        );
        System.out.println(value2);
    }

}

class PredicatorWrapper<T> {

    private Predicate<T> cond;
    private T param;

    public PredicatorWrapper(Predicate<T> cond, T param) {
        this.cond = cond;
        this.param = param;
    }

    public boolean test() {
        return cond.test(param);
    }
}

enum OrderRefundState {
    NORMAL(0),
    PART(11),
    FULL(12);

    private int key;

    OrderRefundState(int key) {
        this.key = key;
    }

    public int key() {
        return key;
    }
}

enum OrderState {
    TOPAY(3),
    CONFIRM(50),
    PAID(5),
    SENT(6);

    private int state;

    OrderState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}

enum OrderType {
    GROUP(10),
    HOTEL(35);

    private int value;

    OrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

enum BizCategoryEnum {
    HOTEL(10);

    private int value;

    BizCategoryEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

enum ExpressType {
    SELF_FETCH(1),
    LOCAL_DELIVERY(2);

    private int key;

    ExpressType(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

@Data
class OrderDetailResponse {
    private int refundState;
    private int orderState;
    private int orderType;
    private int bizType;
    private int expressType;

    Map tcExtra;
    boolean isVirtualOrder;
    boolean isVirtualTicket;

    public boolean noRefund() {
        return refundState == OrderRefundState.NORMAL.key();
    }

    public boolean isToPay() {
        return orderState == OrderState.TOPAY.getState();
    }

    public boolean isGroupOrder() {
        return orderType == OrderType.GROUP.getValue();
    }

    public boolean isHotelOrder() {
        return orderType == OrderType.HOTEL.getValue();
    }
}
