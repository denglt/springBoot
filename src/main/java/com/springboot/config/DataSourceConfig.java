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

    @Bean(name = "firstDataSource")
    @Qualifier("firstDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource firstDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        System.out.println("DataSourceProperties dataSourceProperties()");
        return new DataSourceProperties();
    }


   /*  * 通过DataSourceProperties build datasource
     * @return
     */
    @Bean(name = "secondDataSource")
    @Qualifier("secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource secondDataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }


}
