package com.springboot.restapi;


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

    @Cacheable(value = "user")
    @RequestMapping(value = "/{userId}", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User get(@PathVariable(value = "userId") Integer userId) {
        System.out.println("userId -> " + userId);
        return userService.get(userId);
    }

    @RequestMapping(value = "/xml/{userId}", produces = {MediaType.APPLICATION_XML_VALUE})
    User geXML(@PathVariable("userId") Integer userId) {
        System.out.println("xml");
        return userService.get(userId);
    }

    @RequestMapping("/index")
    String index() {
        return "Hello Spring Boot";
    }

    @RequestMapping(value = "author", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User author() {
        return author;
    }

    @RequestMapping(value = "author2", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User author2() {
        return author2;
    }


    @RequestMapping(value = "error", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User error() throws Exception {
        throw new Exception("error by UserController!");
    }

    @RequestMapping(value = "authorEntity", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<User> authorEntity() {
        int status = 200;
        return new ResponseEntity<>(author, HttpStatus.valueOf(status));
    }


    @RequestMapping(value = "fluxUser", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<User> fluxUser() {   // 【改】返回类型为Mono<String>
        return Flux.just(author, author2);
    }
}
