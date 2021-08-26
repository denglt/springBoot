package com.springboot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
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
// @EnableTransactionManagement // TransactionAutoConfiguration 中自动配置
/**
 *   EnableTransactionManagement 加载：
 *     1、AutoProxyRegistran
 *         AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);

       2、ProxyTransactionManagementConfiguration
        加载：
            BeanFactoryTransactionAttributeSourceAdvisor
            AnnotationTransactionAttributeSource
            TransactionInterceptor
 *
 *
 */
public class TransactionConfig {

    // PlatformTransactionManagerCustomizer

    /**
     *  实现接口 TransactionManagementConfigurer 指定默的TransactionManager
     *  可以把 DataSourceTransactionManager 直接注入到 TransactionInterceptor.transactionManager
     *  @see  TransactionManagementConfigurationSelector
     *  @see  ProxyTransactionManagementConfiguration  使用 TransactionManagementConfigurer 来获取 默认的 PlatformTransactionManager
     *
     * 默认使用： JpaTransactionManager
     * @param dataSource
     * @return
     */
    // 配置默认的 AbstractTransactionManagementConfiguration.PlatformTransactionManager
    @Bean
    public TransactionManagementConfigurer transactionManagementConfigurer(@Qualifier("txManager")PlatformTransactionManager transactionManager) {
        return () -> transactionManager;
    }
    /*
      @Transactional  没有指定 value 是如何获取到 PlatformTransactionManager
      可以跟踪TransactionInterceptor.invoke 来分析下获取 PlatformTransactionManager 顺序：
         1、@Transactional value 指定名称的PlatformTransactionManager
         2、TransactionInterceptor.transactionManagerBeanName 属性指定的
         3、TransactionInterceptor.transactionManager 属性 （该属性默认由TransactionManagementConfigurer注入，见上面分析）
         4、最后beanFactory.getBean(TransactionManager.class)

     */

    @Bean("txManager")
    @Primary     // 由于spring test(5.2.9.RELEASE)获取TransactionManager的实现跟spring tx的实现有差异，故加上@Primary，可以保证两种获取默认的TransactionManager一致
                 // 最新的spring test（master 没有发布）已经修复了TestContextTransactionUtils.retrieveTransactionManager() 中获取TransactionManager的顺序，
                 // 优先获取TransactionManagementConfigurer中的TransactionManager
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return  new DataSourceTransactionManager(dataSource);
    }
    @Bean("txManager2")
    public PlatformTransactionManager transactionManager2(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
