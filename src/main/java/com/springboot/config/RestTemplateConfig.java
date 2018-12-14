package com.springboot.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @see RestTemplateAutoConfiguration
 *
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/11/5 4:12 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class RestTemplateConfig {


    /**
     * 设置RestTemplate属性
     * @return
     */
    @Bean
    RestTemplateCustomizer restTemplateCustomizer(){
        return (restTemplate -> {
          //  restTemplate.setRequestFactory();
          //  restTemplate.setMessageConverters();
            // 把自定义的ClientHttpRequestInterceptor添加到RestTemplate，可添加多个
           // restTemplate.setInterceptors();
        });
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
        // return new RestTemplate(); //这个RestTemplate无法被 RestTemplateCustomizer感知(@see RestTemplateAutoConfiguration)
    }

}
