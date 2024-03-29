package com.springboot.config;

import com.springboot.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.*;

@Configuration
//@ImportResource({"classpath:/spring/scheduler.xml"})  // 导入xml配置文件
public class AppConfig implements InitializingBean {

    public AppConfig(){
        System.out.println("create AppConfig");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AppConfig  afterPropertiesSet() ");
    }

    @Bean("author")
    @Scope("singleton")  // singleton // prototype // session
    public User author() {
        System.out.println("create author");
        User author = new User();
        author.setId(1);
        author.setName("denglt");
        author.setPassword("123456");
        author.setAge(18);
        author.setSex(1);
        author.setRole("user");
        return author;
    }

    @Bean("author2")
    @Scope("singleton")
    public User author2() {
        System.out.println("create author2");
        User author = new User();
        author.setId(2);
        author.setName("zhyy");
        author.setPassword("123456");
        author.setAge(16);
        author.setSex(1);
        author.setRole("user");
        return author;
    }
}
