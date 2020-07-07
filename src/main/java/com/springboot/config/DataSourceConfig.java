package com.springboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 *  see to DataSourceAutoConfiguration
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
    @Primary
    @ConfigurationProperties(prefix = "app.datasource.db1.hikari")
    public DataSource firstDataSource( DataSourceProperties dataSourceProperties) {
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


}
