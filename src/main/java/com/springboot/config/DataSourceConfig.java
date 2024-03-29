package com.springboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * see to DataSourceAutoConfiguration
 *
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/9/19 下午2:13
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "ds1DataSourceProperties")
    @Primary
    @ConfigurationProperties(prefix = "app.datasource.db1")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "firstDataSource")
    //@Primary
    @ConfigurationProperties(prefix = "app.datasource.db1.hikari")
    public DataSource firstDataSource(DataSourceProperties dataSourceProperties) {
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        return dataSource;
    }

    @Bean(name = "ds2DataSourceProperties")
    @ConfigurationProperties(prefix = "app.datasource.db2")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "app.datasource.db2.hikari")
    public DataSource secondDataSource(@Qualifier("ds2DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     *   PlatformTransactionManager 和 Mybatic 配置该 datasource 可以按照交易的读写自动切换数据库，达到读写分离
     * @param masterDataSource
     * @param slaveDataSource
     * @return
     */

    @Primary
    @Bean(name = "transactionRoutingDataSource")
    public DataSource routingDataSource(
            @Qualifier("firstDataSource") @Lazy DataSource masterDataSource,
            @Qualifier("secondDataSource") @Lazy DataSource slaveDataSource
    ) {
        TransactionRoutingDataSource routingDataSource = new TransactionRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.READ_WRITE, masterDataSource);
        dataSourceMap.put(DataSourceType.READ_ONLY, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }
    private class TransactionRoutingDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            printTransaction();
            return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ?  // TransactionSynchronizationManager 在这儿是没有transaction的信息的，
                                                                                       // 因为  org.springframework.transaction.support.AbstractPlatformTransactionManager#getTransaction()中是先调用DataSource.getConnection(),然后才设置TransactionSynchronizationManager
                    DataSourceType.READ_ONLY : DataSourceType.READ_WRITE;
        }

        void printTransaction(){
            System.out.println("TransactionSynchronizationManager.isSynchronizationActive() =" + TransactionSynchronizationManager.isSynchronizationActive());
            System.out.println("TransactionSynchronizationManager.isActualTransactionActive() =" + TransactionSynchronizationManager.isActualTransactionActive());
            System.out.println("TransactionSynchronizationManager.isCurrentTransactionReadOnly() =" + TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        }
    }


    private enum DataSourceType {
        READ_ONLY,
        READ_WRITE
    }

}
