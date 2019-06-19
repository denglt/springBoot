package com.springboot.config;

import com.springboot.restapi.security.UserManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description:
 * @Package: com.springboot.actuator
 * @Author: denglt
 * @Date: 2018/9/18 下午4:53
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
@EnableWebSecurity(debug = true) // 启动Spring Security Debugger @see org.springframework.security.web.debug.DebugFilter
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
        //http.csrf()
        http.authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ADMIN")//ADMIN role can access /admin/**
                //.anyRequest().authenticated()//any other request just need authentication
                .and()
                .formLogin();//enable form login
      // super.configure(http);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);// like @EnableWebSecurity(debug = true)
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
       // return super.userDetailsService();  // 使用本地（spring.security）配置的权限
        return new UserManager();
    }
}
