package zzz.study.patterns.templateMethod.express;

/**
 * Created by shuqin on 17/4/6.
 */
public class CaigouExpress extends AbstractExpress {

  protected int execute(Order order, ExpressParam expressParam) {
    pushMessage(order, expressParam);
    System.out.println("success express for caigou order: " + expressParam);
    return 1;
  }

  private void pushMessage(Order order, ExpressParam expressParam) {
    System.out.println("push message to trigger fenxiao order to express");
  }

}
