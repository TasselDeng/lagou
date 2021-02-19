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

    /**
     * MyBatis一级缓存
     */
    @Test
    public void firstLevelCache() {
        IUserAnnotationMapper userAnnotationMapper = sqlSession.getMapper(IUserAnnotationMapper.class);
        User user1 = userAnnotationMapper.findById(1);
        User user2 = userAnnotationMapper.findById(1);
        System.out.println(user1 == user2);

        User user = new User(10, "Joy11", "12345611", new Date());
        userAnnotationMapper.insertUser(user);
        User user3 = userAnnotationMapper.findById(1);
        System.out.println(user1 == user3);
    }

    /**
     * 二级缓存
     *
     * @throws IOException
     */
    @Test
    public void secondLevelCache() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession1 = sessionFactory.openSession();
        SqlSession sqlSession2 = sessionFactory.openSession();
        SqlSession sqlSession3 = sessionFactory.openSession();

        IUserAnnotationMapper userAnnotationMapper1 = sqlSession1.getMapper(IUserAnnotationMapper.class);
        IUserAnnotationMapper userAnnotationMapper2 = sqlSession2.getMapper(IUserAnnotationMapper.class);
        IUserAnnotationMapper userAnnotationMapper3 = sqlSession3.getMapper(IUserAnnotationMapper.class);

        User user1 = userAnnotationMapper1.findById(1);
        sqlSession1.close();  // 关闭一级缓存
        User user2 = userAnnotationMapper2.findById(1);
        System.out.println(user1 == user2);

        User user = new User(10, "Joy", "12345611", new Date());
        userAnnotationMapper3.updateUser(user);
        User user3 = userAnnotationMapper3.findById(1);
        System.out.println(user1 == user3);
    }
}
