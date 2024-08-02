package com.springboot.config;

import com.springboot.model.User;
import org.springframework.beans.factory.InitializingBean;
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
public class TestConfig implements InitializingBean {

    private final List<User> users;
    public TestConfig(ObjectProvider<List<User>> userProvider){
        System.out.println("create TestConfig");
        users = userProvider.getIfAvailable();
        System.out.println("ObjectProvider<List<User>> userProvider ,能看到下面的TestConfigUser用户吗？注：myUser为static时可以看到");
        users.forEach(System.out::println);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestConfig  afterPropertiesSet() ");
    }
    @Bean
    public static User myUser(){
        User user = new User();
        user.setName("TestConfigUser");
        return user;
    }

    @Bean
    public User myUser2(){
        User user = new User();
        user.setName("TestConfigUser2 ");  // 这个 TestConfig 构造的时候看不到
        return user;
    }
}
