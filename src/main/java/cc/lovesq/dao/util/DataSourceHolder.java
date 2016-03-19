package cc.lovesq.dao.util;

public class DataSourceHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static String getCurrentDataSourceKey() {
        return (String) contextHolder.get();
    }

    public static void setDataSourceKey(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }

}

