package com.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.springboot","com.yuntai","dlt.utils.spring"}
     /* exclude = {DataSourceAutoConfiguration.class} */)
public class DemoApplication  {  // extends SpringBootServletInitializer  for spring boot war(部署到容器时使用)

    private static ConfigurableApplicationContext applicationContext;
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
/*
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("run SpringBootServletInitializer configure");
        return application.sources(DemoApplication.class);
    }
*/

    @RequestMapping("/")
    String index() {
        return "Hello Spring Boot";
    }


    public static void main(String[] args) { // 目前支持同时java -jar *.war 和 在tomcat容器中运行 ( 没有在容器中测试)
        System.out.println("run main");
        // System.setProperty("spring.devtools.restart.enabled", "false");
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        run1(args);
    }

    private static void run1(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class); // AnnotationConfigApplicationContext,AnnotationConfigServletWebServerApplicationContext
      //  springApplication.setBanner((environment, sourceClass, out) -> out.println("hello world by denglt!"));
      //  springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        // springApplication.setApplicationContextClass();
        // springApplication.addInitializers(); // ApplicationContextInitializer
       // springApplication.setAllowBeanDefinitionOverriding(true); // spring boot 2.3.4
        applicationContext = springApplication.run(args);
        //springApplication.setWebApplicationType(WebApplicationType.REACTIVE);
    }

    private static void run2(String[] args) {
         applicationContext = new SpringApplicationBuilder()
                .sources(DemoApplication.class)
                //.child()
                .banner((environment, sourceClass, out) -> out.println("hello world by denglt!"))
                .bannerMode(Banner.Mode.CONSOLE)
                //.contextClass()
                // .initializers()
                // .listeners()
                .logStartupInfo(true)
               // .web(WebApplicationType.REACTIVE)
                .run(args);
    }

    @RequestMapping("/exit")
    void exit(String name) {
        System.out.println("begin close.....");
        System.out.println(applicationContext);
        System.exit(SpringApplication.exit(applicationContext));
    }

    @Bean
    ExitCodeGenerator exitCode() {
        return () -> {
            System.out.println("程序退出!");
            return 1;
        };
    }
}
