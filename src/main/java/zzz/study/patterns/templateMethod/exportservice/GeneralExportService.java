package zzz.study.patterns.templateMethod.exportservice;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shuqin on 18/3/29.
 */
public class GeneralExportService extends AbstractExportService {

    ExecutorService es = Executors.newFixedThreadPool(10);

    @Override
    public void checkBizParam(IExportRequest exportRequest) {
        System.out.println("check general export request");
    }

    @Override
    public ExecutorService getExecutor() {
        return es;
    }
}

@Data
class GeneralExportRequest implements IExportRequest {

    private String bizType;
    private String source;
    private String templateId;

    private String shopId;

    // general export param

}
