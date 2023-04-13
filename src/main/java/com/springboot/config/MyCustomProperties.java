package com.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2023/4/11 17:19
 * @Copyright:
 */
@Configuration
@ConfigurationProperties(prefix = "my.custom")
public class MyCustomProperties {

    private String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
