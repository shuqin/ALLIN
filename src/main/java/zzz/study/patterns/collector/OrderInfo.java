package zzz.study.patterns.collector;

import lombok.Data;

import java.util.List;

@Data
public class OrderInfo {

    private String orderNo;

    public OrderInfo(String orderNo) {
        this.orderNo = orderNo;
    }
}
