package com.lagou.mapper;

import com.lagou.pojo.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.caches.redis.RedisCache;

import java.util.Date;
import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-02-13 22:53
 */
@CacheNamespace
public interface IUserAnnotationMapper {

    @Insert("insert into user values (#{id}, #{username}, #{password}, #{birthday})")
    void insertUser(User user);

    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);

    @Update("update user set username = #{username}, password = #{password}, birthday = #{birthday} where id = #{id}")
    void updateUser(User user);

    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "birthday", column = "birthday", javaType = Date.class),
            @Result(property = "orderList", column = "id", javaType = List.class,
                    many = @Many(select = "com.lagou.mapper.IOrderAnnotationMapper.findByUid")),
    })
    List<User> findAll();
}
