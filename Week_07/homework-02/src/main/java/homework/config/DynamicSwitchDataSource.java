package homework.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * DynamicSwitchDataSource
 *
 * @author qrXun on 2020/12/2
 */
public class DynamicSwitchDataSource extends AbstractRoutingDataSource {

    public DynamicSwitchDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        setTargetDataSources(targetDataSources);
        setDefaultTargetDataSource(defaultTargetDataSource);
        afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDatasourceName();
    }
}
