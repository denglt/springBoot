package com.springboot.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.helper.mybatis.DateToLongTypeHandler;
import org.apache.ibatis.annotations.TypeDiscriminator;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 6060813389471923239L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private Integer sex;
    private String password;
    private String role;
    private String headPhoto;
    private Date createTime ;

    @TableField(jdbcType = JdbcType.BIGINT , typeHandler = DateToLongTypeHandler.class)
    private Date createTimestamp;  // jdbcType ：bigint；
    private Timestamp zoneCreateTime;
    private String  noField;

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

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getZoneCreateTime() {
        return zoneCreateTime;
    }

    public void setZoneCreateTime(Timestamp zoneCreateTime) {
        this.zoneCreateTime = zoneCreateTime;
    }

    public String getNoField() {
        return noField;
    }

    public void setNoField(String noField) {
        this.noField = noField;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", sex=").append(sex);
        sb.append(", password='").append(password).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append(", headPhoto='").append(headPhoto).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", createTimestamp=").append(createTimestamp);
        sb.append(", zoneCreateTime=").append(zoneCreateTime);
        sb.append(", noField='").append(noField).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
