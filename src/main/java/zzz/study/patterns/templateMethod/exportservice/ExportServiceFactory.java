package zzz.study.patterns.templateMethod.exportservice;

/**
 * Created by shuqin on 18/3/30.
 */
public class ExportServiceFactory {

    private static OrderExportService orderExportService;

    private static GeneralExportService generalExportService;

    public static AbstractExportService getExportService(IExportRequest exportRequest) {
        if (exportRequest instanceof OrderExportRequest) {
            return getOrderExportServiceInstance();
        }
        if (exportRequest instanceof GeneralExportRequest) {
            return getGeneralExportServiceInstance();
        }
        throw new IllegalArgumentException("Invalid export request type" + exportRequest.getClass().getName());
    }

    public static OrderExportService getOrderExportServiceInstance() {
        // 实际需要考虑并发, 或者通过Spring容器管理实例
        if (orderExportService != null) {
            return orderExportService;
        }
        return new OrderExportService();
    }

    public static GeneralExportService getGeneralExportServiceInstance() {
        // 实际需要考虑并发, 或者通过Spring容器管理实例
        if (generalExportService != null) {
            return generalExportService;
        }
        return new GeneralExportService();
    }

}
