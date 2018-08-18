package zzz.study.patterns.templateMethod.exportservice;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.ExecutorService;

/**
 * Created by shuqin on 18/3/29.
 */
public abstract class AbstractExportService {

  public String export(IExportRequest exportRequest) {
    checkCommon(exportRequest);
    checkBizParam(exportRequest);
    String exportId = save(exportRequest);
    generateJobFor(exportRequest);
    return exportId;
  }

  private String save(IExportRequest exportRequest) {
    // save export request param into db
    // return exportId
    System.out.println("save export request successfully.");
    return "123";
  }

  private void generateJobFor(IExportRequest exportRequest) {
    getExecutor().execute(() -> exportFor(exportRequest));
    System.out.println("submit export job successfully.");
  }

  public void exportFor(IExportRequest exportRequest) {
    // export for orderExportRequest
    System.out.println("export for export request for" + JSON.toJSONString(exportRequest));
  }

  private void checkCommon(IExportRequest exportRequest) {
    // check bizType
    // check source
    // check templateId
    // check shopId
    System.out.println("check common request passed.");
  }

  public abstract void checkBizParam(IExportRequest exportRequest);
  public abstract ExecutorService getExecutor();

}
