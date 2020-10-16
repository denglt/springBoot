package com.springboot.config;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


/**
 * @Description:   @see  RocketMQAutoConfiguration
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2020/10/15 16:00
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class RocketMQConfig implements InitializingBean {

    @Autowired(required = false)
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (rocketMQTemplate == null){
            //throw  new RuntimeException("请检查Rocket的配置");
        }
    }
}
