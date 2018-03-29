package zzz.study.inf;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Data;

/**
 * Created by shuqin on 18/3/29.
 */
public class OrderExportService extends AbstractExportService {

  ExecutorService es = Executors.newFixedThreadPool(10);

  public static void main(String[] args) {
    new OrderExportService().export(new OrderExportRequest());
  }


  @Override
  public void checkBizParam(IExportRequest exportRequest) {
    System.out.println("check order export request");
  }

  @Override
  public ExecutorService getExecutor() {
    return es;
  }
}

@Data
class OrderExportRequest implements IExportRequest {

  private String bizType;
  private String source;
  private String templateId;

  private String shopId;

  private String orderNos;

  private List<String> orderStates;

}
