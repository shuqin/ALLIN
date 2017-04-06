package zzz.study.patterns.templateMethod.express;

/**
 * Created by shuqin on 17/4/6.
 */
public class Client {

  public static void main(String[] args) {
    ExpressParam expressParam = new ExpressParam("201704062033113366", "1", "666888");
    Express normal = new NormalExpress();
    normal.postExpress(expressParam);

    try {
      Express fenxiao = new FenxiaoExpress();
      expressParam.setOrderNo("F201704062033123456");
      fenxiao.postExpress(expressParam);
    } catch (Exception ex) {
      String exInfo = String.format("Failed to post express for %s , Reason: %s", expressParam, ex.getMessage());
      System.err.println(exInfo);
    }

  }

}
