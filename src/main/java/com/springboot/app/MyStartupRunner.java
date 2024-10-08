package com.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;


@Order(1)
@Component
public class MyStartupRunner implements CommandLineRunner {  // ApplicationRunner

    private static final Logger logger = LoggerFactory.getLogger(MyStartupRunner.class);

    @Value("${application.name}")
    private String appName;


    private String name;


    @Autowired
    private Environment environment;

    public String getName() {
        return name;
    }

    @ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)  // 没有成功
    public void setName(String name) {
        this.name = name;
    }

    @Async
    @Override
    public void run(String... args) {
        logger.info(">>>>>>>>>>>>>>>服务{}启动执行，执行加载数据等操作<<<<<<<<<<<<<", appName);
       // logger.info("redis 配置 -> {}", redisProperties);

        logger.info("===========所有配置信息=============");
        //environment.acceptsProfiles()
        for (String s : environment.getActiveProfiles()) {
            logger.info("profile -> {}", s);
        }

        for (String s : environment.getDefaultProfiles()) {
            logger.info("default profile -> {}", s);
        }

        environment.getProperty("server.port");
        logger.info("server.port -> {}", environment.getProperty("server.port"));
        logger.info("spring.city -> {}", environment.getProperty("spring.city[0]"));
        logger.info("spring citys -> {}", Binder.get(environment).bind("spring.city", Bindable.listOf(String.class)).orElse(null));
        logger.info("city -> {}", environment.getProperty("city[0]"));
        logger.info("acme.list -> {}", environment.getProperty("acme.list"));
        logger.info("acme.list.name -> {}", environment.getProperty("acme.list.name"));
        logger.info("acme.map.key1.name -> {}", environment.getProperty("acme.map.key1.name"));
        logger.info("logging.path -> {}", environment.getProperty("logging.path"));
        logger.info("test.nested -> {}",environment.getProperty("test.nested"));
        logger.info("application.name -> {}",environment.getProperty("application.name"));
        logger.info("appName -> {}",appName);


    }
}
