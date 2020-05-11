package com.springboot.config;

import com.spring.helper.mybatis.DateToLongTypeHandler;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2019-12-16 21:18
 * @Copyright: 版权归 HSYUNTAI 所有
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 *
 *
 */

//@Configuration
@MapperScan("com.springboot.orm")  // 扫描Dao
@MapperScan(value = "com.springboot.orm2", sqlSessionTemplateRef = "mybatis.db2.SqlSessionTemplate")  // 扫描Dao
public class MybatisConfig {

    private final ResourceLoader resourceLoader;
    private final Interceptor[] interceptors;
    private final List<ConfigurationCustomizer> configurationCustomizers;
    private final DatabaseIdProvider databaseIdProvider;
    private final LanguageDriver[] languageDrivers;
    private final TypeHandler[] typeHandlers;

    public MybatisConfig(ObjectProvider<Interceptor[]> interceptorsProvider,
                         ObjectProvider<TypeHandler[]> typeHandlersProvider, ObjectProvider<LanguageDriver[]> languageDriversProvider,
                         ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                         ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        this.resourceLoader = resourceLoader;
        this.interceptors = interceptorsProvider.getIfAvailable();
        this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
        this.languageDrivers = languageDriversProvider.getIfAvailable();
        this.typeHandlers = typeHandlersProvider.getIfAvailable();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "mybatis.db1")
    public MybatisProperties db1Properties() {
        return new MybatisProperties();
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactoryForDb1(DataSource dataSource, MybatisProperties properties) throws Exception {
        checkConfigFileExists(properties);
        return sqlSessionFactory(dataSource, properties);
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplateForDb1(SqlSessionFactory sqlSessionFactory, MybatisProperties properties) {
        return sqlSessionTemplate(sqlSessionFactory, properties);
    }

    @Bean(value = "mybatis.db2.MybatisProperties")
    @ConfigurationProperties(prefix = "mybatis.db2")
    public MybatisProperties db2Properties() {
        return new MybatisProperties();
    }

    @Bean("mybatis.db2.SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryForDb2(@Qualifier("secondDataSource") DataSource dataSource, @Qualifier("mybatis.db2.MybatisProperties") MybatisProperties properties) throws Exception {
        checkConfigFileExists(properties);
        return sqlSessionFactory(dataSource, properties);
    }

    @Bean("mybatis.db2.SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplateForDb2(@Qualifier("mybatis.db2.SqlSessionFactory") SqlSessionFactory sqlSessionFactory, @Qualifier("mybatis.db2.MybatisProperties") MybatisProperties properties) {
        return sqlSessionTemplate(sqlSessionFactory, properties);
    }

    @Bean
    public static TypeHandler typeHandler(){
        return  new DateToLongTypeHandler();
    }

    private SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisProperties properties) {
        ExecutorType executorType = properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }

    private SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisProperties properties) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(properties.getConfigLocation())) {
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
        Set<String> factoryPropertyNames = Stream
                .of(new BeanWrapperImpl(SqlSessionFactoryBean.class).getPropertyDescriptors()).map(PropertyDescriptor::getName)
                .collect(Collectors.toSet());
        Class<? extends LanguageDriver> defaultLanguageDriver = properties.getDefaultScriptingLanguageDriver();
        if (factoryPropertyNames.contains("scriptingLanguageDrivers") && !ObjectUtils.isEmpty(this.languageDrivers)) {
            // Need to mybatis-spring 2.0.2+
            factory.setScriptingLanguageDrivers(this.languageDrivers);
            if (defaultLanguageDriver == null && this.languageDrivers.length == 1) {
                defaultLanguageDriver = this.languageDrivers[0].getClass();
            }
        }
        if (factoryPropertyNames.contains("defaultScriptingLanguageDriver")) {
            // Need to mybatis-spring 2.0.2+
            factory.setDefaultScriptingLanguageDriver(defaultLanguageDriver);
        }

        return factory.getObject();
    }

    private void applyConfiguration(SqlSessionFactoryBean factory, MybatisProperties properties) {
        org.apache.ibatis.session.Configuration configuration = properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(properties.getConfigLocation())) {
            configuration = new org.apache.ibatis.session.Configuration();
        }
        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
                customizer.customize(configuration);
            }
        }
        factory.setConfiguration(configuration);
    }

    private void checkConfigFileExists(MybatisProperties properties) {
        if (properties.isCheckConfigLocation() && StringUtils.hasText(properties.getConfigLocation())) {
            Resource resource = this.resourceLoader.getResource(properties.getConfigLocation());
            Assert.state(resource.exists(),
                    "Cannot find config location: " + resource + " (please add config file or check your Mybatis configuration)");
        }
    }
}
