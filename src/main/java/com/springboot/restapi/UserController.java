package com.springboot.restapi;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.aop.MyAopAnnotation;
import com.springboot.model.User;
import com.springboot.orm.user.UserDao;
import com.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    ServiceImpl<UserDao,User> baseUserService;

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
        //  return userService.getAllFromDb();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","denglt").orderByDesc("id").last("limit 1");
        return baseUserService.list(queryWrapper);
    }

    @RequestMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    List<User> pageFromDB() {
        System.out.println("get all from db");
        //  return userService.getAllFromDb();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","denglt").orderByDesc("id");
        // return baseUserService.list(queryWrapper);
        Page<User> page = new Page(1,3);
        return baseUserService.page(page,queryWrapper).getRecords();
    }



    @RequestMapping(value = "/createTowUser", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    List<User> createTowUser() {
        List<User> userList = new ArrayList<>();
        userList.add(userService.createUserByRandom());
        User newUser = userService.newUserByRandom();
        if (baseUserService.save(newUser)) {
            userList.add(newUser);
        }
        return userList;
    }

    @RequestMapping(value = "/createUser", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    User createUser() {
        System.out.println("create user in db");
        return userService.createUserByRandom();
    }

    @RequestMapping(value = "/createUser2", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    User createUser2() {
        System.out.println("create user in db");
        return userService.createUserByRandom2();
    }

    @RequestMapping(value = "/createUser3", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    User createUserInsertDBNoSucc() {
        System.out.println("create user in db with orm.user.UserDao and txManager2. (数据库应该查询不到这条记录,因为事务和mybatis使用的datasource不同)");
        return userService.createUserByRandomWithOrmUserDaoInTxManager2();
    }

    @RequestMapping(value = "/delete/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    void delete(@PathVariable("id") Long id){
       // userService.delete(id);
        baseUserService.removeById(id);
    }

    @RequestMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    List<User> query(){
        QueryChainWrapper<User> query = baseUserService.query().like("name", "denglt").eq("id", 34);
        return  query.list();
    }
}


