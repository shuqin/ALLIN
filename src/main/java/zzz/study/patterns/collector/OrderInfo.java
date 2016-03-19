package zzz.study.patterns.collector;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderInfo {

    private String orderNo;

    private Map tcExtra;

    public OrderInfo(String orderNo) {
        this.orderNo = orderNo;
    }
}
