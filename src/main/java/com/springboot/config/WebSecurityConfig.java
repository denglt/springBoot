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
        http.csrf().disable() // Spring Security 默认启用了 CSRF 保护，它要求所有修改状态的请求（如 POST、PUT、DELETE 等）都必须包含一个有效的 CSRF 令牌。如果您的 POST 请求中没有包含有效的 CSRF 令牌，那么服务器会拒绝您的请求并返回 403 错误
                .authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ADMIN")//ADMIN role can access /admin/**
                .anyRequest().permitAll()
                //.anyRequest().authenticated()//any other request just need authentication
                .and()
                .formLogin() //enable form login
                .and()
                .httpBasic();
        // super.configure(http);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
     //   web.debug(true);// like @EnableWebSecurity(debug = true)
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
       // return super.userDetailsService();  // 使用本地（spring.security）配置的权限
        return new UserManager();
    }
}
