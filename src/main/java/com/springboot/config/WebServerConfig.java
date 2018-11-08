package com.springboot.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.springboot.restapi.filter.RequestResponseLoggingFilter;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * @see ServerProperties  ServletWebServerFactoryAutoConfiguration
 * @see  WebMvcAutoConfiguration(WebMvcAutoConfigurationAdapter ,EnableWebMvcConfiguration )  HttpMessageConvertersAutoConfiguration
 */
@Configuration
@ServletComponentScan(basePackages = "com.springboot")  // scan @WebServlet, @WebFilter, @WebListener (servlet 3.0)
public class WebServerConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    Logger logger = LoggerFactory.getLogger(this.getClass());
/*
    public WebConfig(RequestMappingHandlerMapping handlerMapping , RequestMappingHandlerAdapter handlerAdapter){
        logger.info("RequestMappingHandlerMapping -> {}" ,handlerMapping);
        logger.info("RequestMappingHandlerAdapter -> {}" ,handlerAdapter);

    }*/
    /**
     * HttpMessageConverter
     * 使用阿里的FastJson进行json (RequestMappingHandlerAdapter.messageConverters)
     *
     *  注意会影响到RestTemplate
     * @return
     */
    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter jsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        return new HttpMessageConverters(false, Collections.singletonList(jsonHttpMessageConverter) );
       // return new HttpMessageConverters(true, Collections.singletonList(jsonHttpMessageConverter) );

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
        if (server instanceof TomcatServletWebServerFactory){
            TomcatServletWebServerFactory tomcatServletWebServerFactory =(TomcatServletWebServerFactory) server;
            tomcatServletWebServerFactory.addConnectorCustomizers( connector -> {
              //  connector.setAsyncTimeout();
              //  connector.setPort();
                ProtocolHandler protocolHandler = connector.getProtocolHandler();
                if (protocolHandler instanceof AbstractProtocol){
                    AbstractProtocol protocol = (AbstractProtocol) protocolHandler;
                    protocol.setKeepAliveTimeout(10000);
                   // protocol.setSoTimeout();

                }
            });
        }
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
