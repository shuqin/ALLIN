package zzz.study.patterns.templateMethod.express;

/**
 * Created by shuqin on 17/4/6.
 */
public class FenxiaoExpress extends AbstractExpress {

    public Order getOrder(String orderNo) {
        return new Order(orderNo, 5);
    }

    protected void checkOrder(Order order) {
        // let order check pass
    }

    protected int execute(Order order, ExpressParam expressParam) {
        System.out.println("success express for fenxiao order: " + expressParam);
        return 1;
    }

}
