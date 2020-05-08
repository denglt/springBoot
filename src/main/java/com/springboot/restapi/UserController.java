package com.springboot.restapi;


import com.springboot.aop.MyAopAnnotation;
import com.springboot.model.User;
import com.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    User author;

    @Autowired
    User author2;

    @Autowired
    UserServiceImpl userService;

    @MyAopAnnotation(value = "u/ser/get")
    @Cacheable(value = "user")
    @RequestMapping(value = "/{userId}", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User get(@PathVariable(value = "userId") Integer userId) {
        System.out.println("userId -> " + userId);
        return userService.get(userId);
    }

    @MyAopAnnotation(value = "/user/xml")
    @RequestMapping(value = "/xml/{userId}", produces = {MediaType.APPLICATION_XML_VALUE})
    User geXML(@PathVariable("userId") Integer userId) {
        System.out.println("xml");
        return userService.get(userId);
    }

    @RequestMapping("/index")
    String index() {
        return "Hello Spring Boot";
    }

    @RequestMapping(value = "/author", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User author() {
        return author;
    }

    @RequestMapping(value = "/author2", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User author2() {
        return author2;
    }


    @RequestMapping(value = "/error", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User error() throws Exception {
        throw new Exception("error by UserController!");
    }

    @RequestMapping(value = "/authorEntity", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<User> authorEntity() {
        int status = 200;
        return new ResponseEntity<>(author, HttpStatus.valueOf(status));
    }


    @RequestMapping(value = "/fluxUser", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<User> fluxUser() {   // 【改】返回类型为Mono<String>
        return Flux.just(author, author2);
    }


    @RequestMapping(value = "/db/{userId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    User getFromDB(@PathVariable("userId") Long userId) {
        System.out.println("from db");
        return userService.getFromDb(userId);
    }

    @RequestMapping(value = "/alldb", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    List<User> getFromDB() {
        System.out.println("get all from db");
        return userService.getAllFromDb();
    }


    @RequestMapping(value = "/createUser", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    User createUserByRandom() {
        System.out.println("create user in db");
        return userService.createUserByRandom();
    }

    @RequestMapping(value = "/createUser2", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    User createUserByRandom2() {
        System.out.println("create user in db with txManager2. (数据库应该查询不到这条记录,因为事务和mybatis使用的datasource不同)");
        return userService.createUserByRandom2();
    }

}


