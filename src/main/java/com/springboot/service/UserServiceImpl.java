package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.model.User;
import com.springboot.orm.user.User2Dao;
import com.springboot.orm.user.UserDao;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService1,UserService2 {

    //  @Autowired
    public User author; // 改为pulbic，测试cglib代理后，proxy是否可以访问? 测试后proxy对象访问该属性为null;

    //  @Autowired
    private User author2;


    private List<User> users = new ArrayList<>();


    @Autowired
    private MeterRegistry meterRegistry;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private UserDao userDao;

    @Autowired
    private User2Dao user2Dao;

    @Autowired
    private com.springboot.orm2.user.UserDao orm2UserDao; // db2

 /*   @Autowired
    private StringRedisTemplate stringRedisTemplate;*/

    private Counter counter;


    @Autowired
    public UserServiceImpl(User author, User author2) {
        System.out.println("UserServiceImpl ->" + author + " ->" + author2);
        this.author = author;
        this.author2 = author2;
        users.add(author);
        users.add(author2);
    }

    @PostConstruct
    public void init() {
        System.out.println("meterRegistry -> " + meterRegistry);
        counter = meterRegistry.counter("UserServiceImpl.getUsers");
        //meterRegistry.config().commonTags("region", "us-east-1");
        for (int i = 2; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("邓_" + i);
            users.add(user);
        }

        User admin = new User();
        admin.setName("admin");
        admin.setPassword("123456");
        admin.setRole("ADMIN");
        users.add(admin);
    }

    @Transactional
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor2() {
        return author2;
    }

    public void setAuthor2(User author2) {
        this.author2 = author2;
    }

    @Transactional(value = "txManager2")
    @Override
    public User get(Integer id) {
        System.out.println("get user -> " + id);
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().get();
    }


    public List<User> getUsers() {
        counter.increment();
        // counter.close();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public User getFromDb(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

    public List<User> getAllFromDb() {
        return userDao.getAll();
    }

    public User newUserByRandom(){
        User newUser = new User();
        newUser.setName("denglt" + System.currentTimeMillis());
        newUser.setPassword(null);
        Date date = new Date();
        newUser.setCreateTime(date);
        newUser.setCreateTimestamp(date);
        newUser.setZoneCreateTime(new Timestamp(date.getTime()));
        newUser.setNoField("nofield");
        return newUser;
    }

    @Transactional(readOnly = true)
    public User createUserByRandom() {
        System.out.println("begin createUserByRandom()");
        printTransaction();
        User newUser = new User();
        newUser.setName("denglt" + System.currentTimeMillis());
        newUser.setPassword(null);
        Date date = new Date();
        newUser.setCreateTime(date);
        newUser.setCreateTimestamp(date);
        newUser.setZoneCreateTime(new Timestamp(date.getTime()));
        newUser.setNoField("nofield");
        userDao.insert(newUser);

        // @TransactionalEventListener 实际上就是变相TransactionSynchronizationManager.registerSynchronization的实现
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() { // 监听 Transaction 状态，执行对应方法
            @Override
            public int getOrder() {
                return super.getOrder();
            }

            @Override
            public void suspend() {
                super.suspend();
            }

            @Override
            public void resume() {
                super.resume();
            }

            @Override
            public void flush() {
                super.flush();
            }

            @Override
            public void beforeCommit(boolean readOnly) {
                super.beforeCommit(readOnly);
            }

            @Override
            public void beforeCompletion() {
                super.beforeCompletion();
            }

            @Override
            public void afterCommit() {
                super.afterCommit();
            }

            @Override
            public void afterCompletion(int status) {
                super.afterCompletion(status);
            }
        });
       // throw new RuntimeException("就是要报错！");
        return newUser;
    }

    void printTransaction(){
        System.out.println("TransactionSynchronizationManager.isSynchronizationActive() =" + TransactionSynchronizationManager.isSynchronizationActive());
        System.out.println("TransactionSynchronizationManager.isActualTransactionActive() =" + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("TransactionSynchronizationManager.isCurrentTransactionReadOnly() =" + TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    }

    @Transactional(value = "txManager2")
    public User createUserByRandomWithOrmUserDaoInTxManager2() {
        User newUser = new User();
        newUser.setName("denglt" + System.currentTimeMillis());
        newUser.setPassword(null);
        Date date = new Date();
        newUser.setCreateTime(date);
        newUser.setCreateTimestamp(date);
        newUser.setZoneCreateTime(new Timestamp(date.getTime()));
        userDao.insert(newUser);
        // throw new RuntimeException("就是要报错！");
        return newUser;
    }

    @Transactional(value = "txManager2")
    public User createUserByRandom2() {
        User newUser = new User();
        newUser.setName("denglt" + System.currentTimeMillis());
        newUser.setPassword(null);
        Date date = new Date();
        newUser.setCreateTime(date);
        newUser.setCreateTimestamp(date);
        newUser.setZoneCreateTime(new Timestamp(date.getTime()));
        orm2UserDao.insert(newUser);
        // throw new RuntimeException("就是要报错！");
        return newUser;
    }

    @Transactional
    public void delete(Long id){
        userDao.deleteById(id);
    }

    @Bean
    public ServiceImpl<UserDao,User> baseUserService(){
      //  return  new ServiceImpl<>(); is error;
        return  new UserServiceImpl2();
    }

    class UserServiceImpl2 extends ServiceImpl<UserDao,User>{

    }
}
