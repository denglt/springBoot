package com.springboot.config;

import com.springboot.model.User;
import org.springframework.context.annotation.*;

@Configuration
@ImportResource({"classpath:/spring/scheduler.xml"})  // 导入xml配置文件
public class AppConfig {

    @Bean("author2")
    @Scope("singleton")
    public User author() {
        System.out.println("create author2");
        User author = new User();
        author.setId(2);
        author.setName("zhyy");
        author.setPasswword("123456");
        author.setAge(16);
        author.setSex(1);
        return author;
    }
}
