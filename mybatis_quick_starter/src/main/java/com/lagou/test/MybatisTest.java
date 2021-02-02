package com.lagou.test;

import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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

    @Test
    public void test1() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
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
}
