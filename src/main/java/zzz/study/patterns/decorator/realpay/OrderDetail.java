package zzz.study.patterns.decorator.realpay;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetail {

    private Integer orderType;
    private String buyWay;
}
