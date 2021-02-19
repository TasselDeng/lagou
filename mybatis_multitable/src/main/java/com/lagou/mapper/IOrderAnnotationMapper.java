package com.lagou.mapper;

import com.lagou.pojo.Order;
import com.lagou.pojo.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-02-13 22:53
 */
public interface IOrderAnnotationMapper {

    @Select("select * from orders")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ordertime", column = "ordertime", javaType = Date.class),
            @Result(property = "total", column = "total"),
            @Result(property = "user", column = "uid", javaType = User.class,
                    one = @One(select = "com.lagou.mapper.IUserAnnotationMapper.findById")),
    })
    List<Order> findAll();

    @Select("select * from orders where uid = #{uid}")
    List<Order> findByUid(Integer uid);
}
