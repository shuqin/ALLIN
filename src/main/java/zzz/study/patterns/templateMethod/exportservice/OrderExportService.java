package zzz.study.patterns.templateMethod.exportservice;

import lombok.Data;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shuqin on 18/3/29.
 */
public class OrderExportService extends AbstractExportService {

    ExecutorService es = Executors.newCachedThreadPool();

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
