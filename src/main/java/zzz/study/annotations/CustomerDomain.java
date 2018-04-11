package zzz.study.annotations;

import java.util.List;

import lombok.Data;

/**
 * Created by shuqin on 18/4/11.
 */
@Data
public class CustomerDomain implements DomainSearch {

  /** 店铺ID */
  @EsField(name="shop_id", required = true)
  private Long shopId;

  /** 订单编号 */
  @EsField(name="order_no")
  private String orderNo;

  /** 订单状态 */
  @EsField(name="state", type="list")
  private List<Integer> state;

  /** 订单类型 */
  @EsField(name="order_type", type="list")
  private List<Integer> orderType;

}
