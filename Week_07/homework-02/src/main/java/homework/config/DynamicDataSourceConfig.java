package homework.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * DynamicDataSourceConfig
 *
 * @author qrXun on 2020/12/2
 */
@Configuration
public class DynamicDataSourceConfig {

    @Value("${mybatis.config-location}")
    private String configLocation;

    @Bean(name = "master")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "second")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource slaveOneDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicSwitchDataSource dynamicDataSource(DataSource master, DataSource second){
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceNameEnum.MASTER.name().toLowerCase(), master);
        targetDataSource.put(DataSourceNameEnum.SECOND.name().toLowerCase(), second);
        return new DynamicSwitchDataSource(master, targetDataSource);
    }

    /**
     * @description: 配置mybatis的mapper和dao的位置
     */
    @Bean("sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DynamicSwitchDataSource dynamicDataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(configLocation));
        return sqlSessionFactoryBean;
    }

    /**
     * 事务管理
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource") DynamicSwitchDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }


}
