package com.springboot.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.springboot.restapi.filter.RequestResponseLoggingFilter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@ServletComponentScan(basePackages = "com.springboot")  // scan @WebServlet, @WebFilter, @WebListener (servlet 3.0)
public class WebConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    /**
     * HttpMessageConverter
     * 使用阿里的FastJson进行json
     *
     * @return
     */
    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter jsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        return new HttpMessageConverters(jsonHttpMessageConverter);
    }

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
     * 跨域配置   @CrossOrigin
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                CorsRegistration corsRegistration = registry.addMapping("/api/**");
                //  corsRegistration.allowedOrigins() like
            }
        };
    }

    /**
     *  Customize the specified WebServerFactory
     * @param server
     */

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        System.out.println("WebServerFactory -> " + server);
        server.setPort(9000);
    }
/*
Customizing ConfigurableServletWebServerFactory Directly

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(9000);
       // factory.setSessionTimeout(10, TimeUnit.MINUTES);
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
        return factory;
    }*/
}
