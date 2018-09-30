package com.springboot.actuator;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Package: com.springboot.actuator
 * @Author: denglt
 * @Date: 2018/9/18 下午4:53
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Component
public class ActuatorSecurity extends WebSecurityConfigurerAdapter {

    //@Override
    protected void configure_del(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ADMIN")//ADMIN role can access /admin/**
                .anyRequest().authenticated()//any other request just need authentication
                .and()
                .formLogin();//enable form login

    }
}
