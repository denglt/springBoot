package com.springboot.config;


import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

/**
 *  see to RedisAutoConfiguration
 */
@Configuration
@PropertySource("classpath:redis.properties")  // 不支持 yaml；   不支持 @Value和 支持Environment(没亲自测试)
public class RedisConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisProperties redisProperties() {
        return new RedisProperties();
    }
}
