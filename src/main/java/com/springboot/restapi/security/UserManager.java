package com.springboot.restapi.security;

import com.springboot.model.User;
import com.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 登录成功发送：AuthenticationSuccessEvent
 *
 * @Description:
 * @Package: com.springboot.restapi.security
 * @Author: denglt
 * @Date: 2018/9/14 下午4:44
 * @Copyright: 版权归 HSYUNTAI 所有
 */

public class UserManager implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    /**
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("user name -> " + userName);
        Optional<User> findUser = userService.getUsers().stream().filter(u -> u.getName().equals(userName)).findFirst();
        if (findUser.isPresent()) {
            User u = findUser.get();
            return org.springframework.security.core.userdetails.User
                    // .withDefaultPasswordEncoder()
                    .withUsername(u.getName())
                    .password(u.getPasswword())
                    .passwordEncoder(p -> { // 可以修改DaoAuthenticationProvider.PasswordEncoder, 使两者一致
                        MessageDigestPasswordEncoder md5 = new MessageDigestPasswordEncoder("MD5");
                        return "{MD5}" + md5.encode(p);
                    })
                    .roles(u.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException("no find user");
        }
    }
}
