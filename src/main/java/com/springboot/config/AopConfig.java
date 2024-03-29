package com.springboot.config;

import com.springboot.aop.MyAopBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/12/25 5:24 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class AopConfig {

    public AopConfig(){
        System.out.println("create AopConfig");
    }


    //@Bean
    public MyAopBeanPostProcessor myAopBeanPostProcessor(){
        return new MyAopBeanPostProcessor();
    }
}
