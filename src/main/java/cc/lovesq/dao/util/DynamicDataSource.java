package cc.lovesq.dao.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = DataSourceHolder.getCurrentDataSourceKey();
        return dataSourceKey;
    }

}

