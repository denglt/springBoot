package com.springboot.config;

import com.springboot.restapi.filter.RequestResponseLoggingFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2019-08-27 16:17
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configurable
public class WebFilterConfig {

    /**
     * 配置Filter
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestResponseLoggingFilter());
        registrationBean.addUrlPatterns("/user/*");

        return registrationBean;
    }

    /**
     * 字符集过滤器
     * HttpEncodingAutoConfiguration 中自动配置了 OrderedCharacterEncodingFilter
     */
}
