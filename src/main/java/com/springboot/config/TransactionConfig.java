package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * 入口 @see  TransactionAutoConfiguration (JtaAutoConfiguration ,  DataSourceTransactionManagerAutoConfiguration)
 *
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/11/7 10:48 AM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
// @EnableTransactionManagement
public class TransactionConfig {

    // PlatformTransactionManagerCustomizer

    /**
     *  可以把 DataSourceTransactionManager 直接注入到 TransactionInterceptor.transactionManager
     *  @see  TransactionManagementConfigurationSelector
     *  @see  ProxyTransactionManagementConfiguration
     *
     * 默认使用： JpaTransactionManager
     * @param dataSource
     * @return
     */
    @Bean
    public TransactionManagementConfigurer transactionManagementConfigurer(DataSource dataSource) {
        return () -> new DataSourceTransactionManager(dataSource);
    }

/*    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/
}
