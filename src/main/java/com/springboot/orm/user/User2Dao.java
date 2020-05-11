package com.springboot.orm.user;

import com.springboot.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface User2Dao {

    @Delete("delete from user where id = #{id}")
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(User record);

    int insertSelective(User record);

    @Select("select * from user where id = #{id}")
    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}