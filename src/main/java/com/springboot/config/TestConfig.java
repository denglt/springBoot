package com.springboot.config;

import com.springboot.model.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description:
 * @Package: com.springboot.config
 * @Author: denglt
 * @Date: 2020/5/11 15:33
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class TestConfig {

    private final List<User> users;
    public TestConfig(ObjectProvider<List<User>> userProvider){
        users = userProvider.getIfAvailable();
        System.out.println("ObjectProvider<List<User>> userProvider ,能看到下面的TestConfigUser用户吗？注：myUser为static时可以看到");
        users.forEach(System.out::println);
    }

    @Bean
    public static User myUser(){
        User user = new User();
        user.setName("TestConfigUser");
        return user;
    }
}
