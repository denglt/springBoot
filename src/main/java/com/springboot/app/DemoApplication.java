package com.springboot.app;

import com.springboot.model.User;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintStream;


@EnableConfigurationProperties
@ComponentScan(value = {"com.springboot"})
@RestController
@EnableAutoConfiguration(/*exclude = {DataSourceAutoConfiguration.class}*/)
@SpringBootApplication
public class DemoApplication {

    private static ConfigurableApplicationContext applicationContext;

    @RequestMapping("/")
    String index() {
        return "Hello Spring Boot";
    }

    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
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
                .run(args);
    }

    @Bean("author")
    @Scope("singleton")  // singleton // prototype // session
    public User author() {
        System.out.println("create author");
        User author = new User();
        author.setId(1);
        author.setName("denglt");
        author.setPasswword("123456");
        author.setAge(18);
        author.setSex(1);
        return author;
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
