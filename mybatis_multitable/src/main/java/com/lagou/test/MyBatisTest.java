package com.lagou.test;

import com.lagou.mapper.IOrderAnnotationMapper;
import com.lagou.mapper.IOrderMapper;
import com.lagou.mapper.IUserAnnotationMapper;
import com.lagou.mapper.IUserMapper;
import com.lagou.pojo.Order;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-02-12 23:13
 */
public class MyBatisTest {

    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sessionFactory.openSession();
    }

    /**
     * 一对一查询，一个订单对一个用户
     */
    @Test
    public void test1() {
        IOrderMapper orderMapper = sqlSession.getMapper(IOrderMapper.class);
        List<Order> orders = orderMapper.findAll();
        orders.forEach(System.out::println);
    }

    /**
     * 一对多查询，一个用户对多个订单
     */
    @Test
    public void test2() {
        IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
        List<User> userList = userMapper.findAll();
        userList.forEach(System.out::println);
    }

    /**
     * 多对多查询，所有用户对应的角色
     */
    @Test
    public void test3() {
        IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
        List<User> userList = userMapper.findAllUserAndRole();
        userList.forEach(System.out::println);
    }

    /**
     * MyBatis注解
     */
    @Test
    public void useAnnotation() {
        IUserAnnotationMapper userAnnotationMapper = sqlSession.getMapper(IUserAnnotationMapper.class);
        User user = new User(10, "Joy11", "12345611", new Date());
//        userAnnotationMapper.insertUser(user);
//        userAnnotationMapper.updateUser(user);
        userAnnotationMapper.deleteUser(10);
        sqlSession.commit();
    }

    /**
     * MyBatis注解一对一
     */
    @Test
    public void useAnnotation2() {
        IOrderAnnotationMapper orderAnnotationMapper = sqlSession.getMapper(IOrderAnnotationMapper.class);
        List<Order> orderList = orderAnnotationMapper.findAll();
        orderList.forEach(System.out::println);
    }

    /**
     * MyBatis注解一对多
     */
    @Test
    public void useAnnotation3() {
        IUserAnnotationMapper userAnnotationMapper = sqlSession.getMapper(IUserAnnotationMapper.class);
        List<User> userList = userAnnotationMapper.findAll();
        userList.forEach(System.out::println);
    }
}
