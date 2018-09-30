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
@PropertySource("classpath:redis.properties")  // 不支持 yaml
@Validated
public class RedisConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "redis")
    public RedisProperties redisProperties() {
        return new RedisProperties();
    }
}
