package com.springboot.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.springboot.restapi.filter.RequestResponseLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2018/11/8 11:11 AM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

/**
 * WebMvc的配置  （spring @EnableWebMvc -> WebMvcConfigurationSupport -> RequestMappingHandlerMapping ）
 *
 * Spring boot  @see WebMvcAutoConfiguration (WebMvcAutoConfigurationAdapter ,EnableWebMvcConfiguration )  HttpMessageConvertersAutoConfiguration
 *
 */
@Configuration
//@ServletComponentScan(basePackages = "com.springboot")  // scan @WebServlet, @WebFilter, @WebListener (servlet 3.0)
public class WebMvcConfig implements WebMvcConfigurer {

    private static Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    /**
     * HttpMessageConverter
     * 使用阿里的FastJson进行json (RequestMappingHandlerAdapter.messageConverters)
     * <p>
     * 注意会影响到RestTemplate
     *
     * @return
     */
    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter jsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        HttpMessageConverter jaxb2RootElementHttpMessageConverter = new Jaxb2RootElementHttpMessageConverter();
        HttpMessageConverter sourceHttpMessageConverter = new SourceHttpMessageConverter();
        List<HttpMessageConverter<?>> converterList = new ArrayList<>();
        converterList.add(jsonHttpMessageConverter);
       // converterList.add(jaxb2RootElementHttpMessageConverter);
        converterList.add(sourceHttpMessageConverter);
       // return new HttpMessageConverters(false, converterList);
         return new HttpMessageConverters(true, Collections.singletonList(jsonHttpMessageConverter) );

    }



    /**
     * 跨域配置   @CrossOrigin
     *
     * @return
     */
/*    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                CorsRegistration corsRegistration = registry.addMapping("/api/**");
                //  corsRegistration.allowedOrigins() like
            }
        };
    }*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping("/api/**");
        //  corsRegistration.allowedOrigins()
    }

    /**
     * 配置 Interceptors
     *
     * @return
     */

/*    @Bean
    public WebMvcConfigurer addInterceptors() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        System.out.println("HandlerInterceptor preHandle!");
                        return false;
                    }
                });
            }
        };
    }*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLogHandlerInterceptor()).addPathPatterns("/user/*");

        registry.addInterceptor(new AsyncLogHandlerInterceptor()).addPathPatterns("/async/*");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.add
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    class UserLogHandlerInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            logger.info("UserLogHandlerInterceptor -> preHandle");
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            // 这儿的参数还有ModleAndView,故该方法在render the view 之前执行
            logger.info("UserLogHandlerInterceptor -> postHandle");
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            logger.info("UserLogHandlerInterceptor -> afterCompletion ;" + ex);
        }
    }

    class AsyncLogHandlerInterceptor implements AsyncHandlerInterceptor {

        @Override
        public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            logger.info("AsyncLogHandlerInterceptor -> afterConcurrentHandlingStarted");
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            logger.info("AsyncLogHandlerInterceptor -> preHandle");
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            logger.info("AsyncLogHandlerInterceptor -> postHandle");
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            logger.info("AsyncLogHandlerInterceptor -> afterCompletion");
        }
    }
}
/**
 一个Async Request 的日志：
 2018-11-08 15:06:47.006  INFO 6489 --- [nio-9000-exec-9] c.s.restapi.filter.TransactionFilter     : Starting a transaction for req : /springboot/async/shortCallable
 2018-11-08 15:06:47.008  INFO 6489 --- [nio-9000-exec-9] com.springboot.config.WebMvcConfig       : AsyncLogHandlerInterceptor -> preHandle  // 执行了两次
 2018-11-08 15:06:47.008  INFO 6489 --- [nio-9000-exec-9] com.springboot.restapi.AsyncController   : Entering controller
 2018-11-08 15:06:47.008  INFO 6489 --- [nio-9000-exec-9] com.springboot.restapi.AsyncController   : Leaving  controller
 2018-11-08 15:06:47.009  INFO 6489 --- [nio-9000-exec-9] com.springboot.config.WebMvcConfig       : AsyncLogHandlerInterceptor -> afterConcurrentHandlingStarted
 2018-11-08 15:06:47.009  INFO 6489 --- [      MvcAsync3] com.springboot.restapi.AsyncController   : begin call() ...
 2018-11-08 15:06:47.009  INFO 6489 --- [nio-9000-exec-9] c.springboot.event.LogApplicationEvent   : ApplicationEvent -> ServletRequestHandledEvent: url=[/springboot/async/shortCallable]; client=[127.0.0.1]; method=[GET]; servlet=[dispatcherServlet]; session=[A9C9C2D2207BD18E3950A1D83B56D5CC]; user=[admin]; time=[2ms]; status=[OK]
 2018-11-08 15:06:47.009  INFO 6489 --- [nio-9000-exec-9] c.s.restapi.filter.TransactionFilter     : Committing a transaction for req : /springboot/async/shortCallable
 2018-11-08 15:06:57.011  INFO 6489 --- [      MvcAsync3] com.springboot.restapi.AsyncController   : end call() !
 2018-11-08 15:06:57.015  INFO 6489 --- [io-9000-exec-10] com.springboot.config.WebMvcConfig       : AsyncLogHandlerInterceptor -> preHandle   // 执行了两次
 2018-11-08 15:06:57.016  INFO 6489 --- [io-9000-exec-10] com.springboot.config.WebMvcConfig       : AsyncLogHandlerInterceptor -> postHandle
 2018-11-08 15:06:57.016  INFO 6489 --- [io-9000-exec-10] com.springboot.config.WebMvcConfig       : AsyncLogHandlerInterceptor -> afterCompletion
 2018-11-08 15:06:57.016  INFO 6489 --- [io-9000-exec-10] c.springboot.event.LogApplicationEvent   : ApplicationEvent -> ServletRequestHandledEvent: url=[/springboot/async/shortCallable]; client=[127.0.0.1]; method=[GET]; servlet=[dispatcherServlet]; session=[A9C9C2D2207BD18E3950A1D83B56D5CC]; user=[admin]; time=[2ms]; status=[OK]

 */



