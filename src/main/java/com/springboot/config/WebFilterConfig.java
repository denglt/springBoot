package com.springboot.config;

import com.springboot.restapi.filter.RequestResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2019-08-27 16:17
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class WebFilterConfig {

    /**
     * 配置Filter  , 也可以使用DelegatingFilterProxyRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestResponseLoggingFilter());
        registrationBean.addUrlPatterns("/user/*");
        // 配置经过filter的类型
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR);

        return registrationBean;
    }

    /**
     * 字符集过滤器
     * HttpEncodingAutoConfiguration 中自动配置了 OrderedCharacterEncodingFilter
     */
}
