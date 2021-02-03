package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-02-02 22:09
 */
public class MybatisTest {

    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sessionFactory.openSession();
    }

    @Test
    public void test1() {

//        List<User> userList = sqlSession.selectList("user.findAll");
//        userList.forEach(System.out::println);

        User user = new User();
        user.setId(5);
        user.setUsername("ying");
//        user.setUsername("dengsys");
//        sqlSession.insert("user.saveUser", user);

        sqlSession.update("user.updateUser", user);
        sqlSession.delete("user.deleteUser", user);
        sqlSession.commit();
    }

    @Test
    public void test2() {
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//        List<User> userList = userDao.findAll();
//        userList.forEach(System.out::println);
        User user = new User();
        user.setId(1);
        User user1 = userDao.findById(user);
        System.out.println(user1);
    }
}
