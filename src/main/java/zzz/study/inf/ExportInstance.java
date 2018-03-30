package zzz.study.inf;

/**
 * Created by shuqin on 18/3/30.
 */
public class ExportInstance {

  public static void main(String[] args) {
    OrderExportRequest orderExportRequest = new OrderExportRequest();
    ExportServiceFactory.getExportService(orderExportRequest).export(orderExportRequest);

    GeneralExportRequest generalExportRequest = new GeneralExportRequest();
    ExportServiceFactory.getExportService(generalExportRequest).export(generalExportRequest);
  }

}
