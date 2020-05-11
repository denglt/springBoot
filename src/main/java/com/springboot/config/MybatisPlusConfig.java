package com.springboot.config;

import com.baomidou.mybatisplus.autoconfigure.*;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.spring.helper.mybatis.DateToLongTypeHandler;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2020/5/9 16:31
 * @Copyright: 版权归 HSYUNTAI 所有
 * @see com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration
 */

@Configuration
@MapperScan("com.springboot.orm")  // 扫描Dao
@MapperScan(value = "com.springboot.orm2", sqlSessionTemplateRef = "mybatis.db2.SqlSessionTemplate")  // 扫描Dao
public class MybatisPlusConfig {

    private static final Logger logger = LoggerFactory.getLogger(MybatisPlusConfig.class);


    private final Interceptor[] interceptors;

    private final TypeHandler[] typeHandlers;

    private final LanguageDriver[] languageDrivers;

    private final ResourceLoader resourceLoader;

    private final DatabaseIdProvider databaseIdProvider;

    private final List<ConfigurationCustomizer> configurationCustomizers;

    private final List<MybatisPlusPropertiesCustomizer> mybatisPlusPropertiesCustomizers;

    private final ApplicationContext applicationContext;


    public MybatisPlusConfig(ObjectProvider<Interceptor[]> interceptorsProvider,
                             ObjectProvider<TypeHandler[]> typeHandlersProvider,
                             ObjectProvider<LanguageDriver[]> languageDriversProvider,
                             ResourceLoader resourceLoader,
                             ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                             ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
                             ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider,
                             ApplicationContext applicationContext) {

        this.interceptors = interceptorsProvider.getIfAvailable();
        this.typeHandlers = typeHandlersProvider.getIfAvailable();
        this.languageDrivers = languageDriversProvider.getIfAvailable();
        this.resourceLoader = resourceLoader;
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
        this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
        this.mybatisPlusPropertiesCustomizers = mybatisPlusPropertiesCustomizerProvider.getIfAvailable();
        this.applicationContext = applicationContext;
    }



    @Bean
    @Primary
    @ConfigurationProperties(prefix = "mybatis.db1")
    public MybatisPlusProperties db1Properties() {
        return new MybatisPlusProperties();
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactoryForDb1(DataSource dataSource, MybatisPlusProperties properties) throws Exception {
        customizeProperties(properties);
        checkConfigFileExists(properties);
        return sqlSessionFactory(dataSource, properties);
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplateForDb1(SqlSessionFactory sqlSessionFactory, MybatisPlusProperties properties) {
        return sqlSessionTemplate(sqlSessionFactory, properties);
    }

    @Bean(value = "mybatis.db2.MybatisProperties")
    @ConfigurationProperties(prefix = "mybatis.db2")
    public MybatisPlusProperties db2Properties() {
        return new MybatisPlusProperties();
    }

    @Bean("mybatis.db2.SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryForDb2(@Qualifier("secondDataSource") DataSource dataSource, @Qualifier("mybatis.db2.MybatisProperties") MybatisPlusProperties properties) throws Exception {
        customizeProperties(properties);
        checkConfigFileExists(properties);
        return sqlSessionFactory(dataSource, properties);
    }

    @Bean("mybatis.db2.SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplateForDb2(@Qualifier("mybatis.db2.SqlSessionFactory") SqlSessionFactory sqlSessionFactory, @Qualifier("mybatis.db2.MybatisProperties") MybatisPlusProperties properties) {
        return sqlSessionTemplate(sqlSessionFactory, properties);
    }

    @Bean
    public static TypeHandler typeHandler(){
        return  new DateToLongTypeHandler();
    }

    private void customizeProperties(MybatisPlusProperties properties) {
        if (!CollectionUtils.isEmpty(mybatisPlusPropertiesCustomizers)) {
            mybatisPlusPropertiesCustomizers.forEach(i -> i.customize(properties));
        }
    }

    private void checkConfigFileExists(MybatisPlusProperties properties) {
        if (properties.isCheckConfigLocation() && StringUtils.hasText(properties.getConfigLocation())) {
            Resource resource = this.resourceLoader.getResource(properties.getConfigLocation());
            Assert.state(resource.exists(),
                    "Cannot find config location: " + resource + " (please add config file or check your Mybatis configuration)");
        }
    }


    private SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisPlusProperties properties) throws Exception {
        // TODO 使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(properties.getConfigLocation())){
            factory.setConfigLocation(this.resourceLoader.getResource(properties.getConfigLocation()));
        }
        applyConfiguration(factory, properties);
        if (properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(properties.getConfigurationProperties());
        }
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            factory.setPlugins(this.interceptors);
        }
        if (this.databaseIdProvider != null) {
            factory.setDatabaseIdProvider(this.databaseIdProvider);
        }
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        if (properties.getTypeAliasesSuperType() != null) {
            factory.setTypeAliasesSuperType(properties.getTypeAliasesSuperType());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.typeHandlers)) {
            factory.setTypeHandlers(this.typeHandlers);
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factory.setMapperLocations(properties.resolveMapperLocations());
        }

        // TODO 对源码做了一定的修改(因为源码适配了老旧的mybatis版本,但我们不需要适配)
        Class<? extends LanguageDriver> defaultLanguageDriver = properties.getDefaultScriptingLanguageDriver();
        if (!ObjectUtils.isEmpty(this.languageDrivers)) {
            factory.setScriptingLanguageDrivers(this.languageDrivers);
        }
        Optional.ofNullable(defaultLanguageDriver).ifPresent(factory::setDefaultScriptingLanguageDriver);

        // TODO 自定义枚举包
        if (StringUtils.hasLength(properties.getTypeEnumsPackage())) {
            factory.setTypeEnumsPackage(properties.getTypeEnumsPackage());
        }
        // TODO 此处必为非 NULL
        GlobalConfig globalConfig = properties.getGlobalConfig();
        // TODO 注入填充器
        this.getBeanThen(MetaObjectHandler.class, globalConfig::setMetaObjectHandler);
        // TODO 注入主键生成器
        this.getBeanThen(IKeyGenerator.class, i -> globalConfig.getDbConfig().setKeyGenerator(i));
        // TODO 注入sql注入器
        this.getBeanThen(ISqlInjector.class, globalConfig::setSqlInjector);
        // TODO 注入ID生成器
        this.getBeanThen(IdentifierGenerator.class, globalConfig::setIdentifierGenerator);
        // TODO 设置 GlobalConfig 到 MybatisSqlSessionFactoryBean
        factory.setGlobalConfig(globalConfig);
        return factory.getObject();
    }

    /**
     * 检查spring容器里是否有对应的bean,有则进行消费
     *
     * @param clazz    class
     * @param consumer 消费
     * @param <T>      泛型
     */
    private <T> void getBeanThen(Class<T> clazz, Consumer<T> consumer) {
        if (this.applicationContext.getBeanNamesForType(clazz, false, false).length > 0) {
            consumer.accept(this.applicationContext.getBean(clazz));
        }
    }

    // TODO 入参使用 MybatisSqlSessionFactoryBean
    private void applyConfiguration(MybatisSqlSessionFactoryBean factory, MybatisPlusProperties properties) {
        // TODO 使用 MybatisConfiguration
        MybatisConfiguration configuration = properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(properties.getConfigLocation())) {
            configuration = new MybatisConfiguration();
        }
        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
                customizer.customize(configuration);
            }
        }
        factory.setConfiguration(configuration);
    }

    private SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisPlusProperties properties) {
        ExecutorType executorType = properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }

}
