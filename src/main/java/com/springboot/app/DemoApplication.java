package com.springboot.app;

import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintStream;


@EnableConfigurationProperties
@ComponentScan(value = {"com.springboot"})
@RestController
@EnableAutoConfiguration(/*exclude = {DataSourceAutoConfiguration.class}*/) // spring.factories 文件中配置了  Auto Configure
@EnableCaching
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class DemoApplication {  // SpringBootServletInitializer  for spring boot war

    private static ConfigurableApplicationContext applicationContext;

    @RequestMapping("/")
    String index() {
        return "Hello Spring Boot";
    }

    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        run2(args);
    }

    private static void run1(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        springApplication.setBanner(new Banner() {
            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                out.println("hello world by denglt!");
            }
        });
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
