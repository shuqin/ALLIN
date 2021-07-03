package zzz.study.patterns.templateMethod.express;

/**
 * Created by shuqin on 17/4/6.
 * Provide a default implementation of Express
 */
public abstract class AbstractExpress implements Express {

    public int postExpress(ExpressParam expressParam) {
        checkExpressParam(expressParam);
        Order order = getOrder(expressParam.getOrderNo());
        checkOrder(order);
        return execute(order, expressParam);
    }

    protected void checkExpressParam(ExpressParam expressParam) {
        // basic express param check, probably not be overriden
    }

    protected void checkOrder(Order order) {
        // check if order can express. may be overriden
        if (Integer.valueOf(5).equals(order.getOrderType()) || order.getOrderNo().startsWith("F")) {
            throw new IllegalArgumentException("Fenxiao order can not be expressed by own");
        }
    }

    protected Order getOrder(String orderNo) {
        // here is just for creating order , probably not overriden
        return new Order(orderNo, 0);
    }

    /**
     * 发货的默认实现
     *
     * @param order        订单信息
     * @param expressParam 发货参数
     * @return 发货包裹ID
     * <p>
     * Note: Suggest this method be overriden !
     */
    protected int execute(Order order, ExpressParam expressParam) {
        System.out.println("success express for normal order: " + expressParam);
        return 1;
    }

}
