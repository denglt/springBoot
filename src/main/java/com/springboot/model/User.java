package com.springboot.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 6060813389471923239L;
    private Integer id;
    private String name;
    private Integer age;
    private Integer sex;
    private String password;
    private String role;
    private String headPhoto;
    private Date createTime ;
    private Long createTimestamp;
    private Timestamp zoneCreateTime;

    public User(){

    }


    public User(String userName){
        this.name = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getZoneCreateTime() {
        return zoneCreateTime;
    }

    public void setZoneCreateTime(Timestamp zoneCreateTime) {
        this.zoneCreateTime = zoneCreateTime;
    }
}
