package zzz.spark;

import lombok.Data;

@Data
public class RefundInfo {

    private String type;       // 退款方式
    private String orderNo;    // 订单编号
    private String goodsTitle; // 商品名称
    private Double realPrice;  // 订单金额
    private Double refund;     // 退款金额

    public static RefundInfo from(String[] arr) {
        if (arr == null || arr.length != 5) {
            return null;
        }
        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setType(arr[0]);
        refundInfo.setOrderNo(arr[1]);
        refundInfo.setGoodsTitle(arr[2]);
        refundInfo.setRealPrice(Double.valueOf(arr[3]));
        refundInfo.setRefund(Double.valueOf(arr[4]));
        return refundInfo;
    }

}
