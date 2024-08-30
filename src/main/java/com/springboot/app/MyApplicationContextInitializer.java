package com.springboot.app;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description:
 * @Package: com.springboot.app
 * @Author: denglt
 * @Date: 2024/8/6 11:26
 * @Copyright:
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("-----MyApplicationContextInitializer initialize-----");
    }
}
