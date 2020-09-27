package com.springboot.app;

import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableConfigurationProperties
@ComponentScan(value = {"com.springboot"})
@RestController
@EnableAutoConfiguration(/*exclude = {DataSourceAutoConfiguration.class}*/) // spring.factories 文件中配置了  Auto Configure
@EnableCaching
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class DemoApplication  {  // extends SpringBootServletInitializer  for spring boot war(部署到容器时使用)

    private static ConfigurableApplicationContext applicationContext;

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
        springApplication.setBanner((environment, sourceClass, out) -> out.println("hello world by denglt!"));
      //  springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        // springApplication.setApplicationContextClass();
        // springApplication.addInitializers();
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
