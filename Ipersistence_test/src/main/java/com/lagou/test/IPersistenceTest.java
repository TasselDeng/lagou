package com.lagou.test;

import com.lagou.io.Resources;
import com.lagou.mapper.IUserDao;
import com.lagou.pojo.User;
import com.lagou.sqlsession.SqlSession;
import com.lagou.sqlsession.SqlSessionFactory;
import com.lagou.sqlsession.SqlSessionFactoryBuild;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-01-24 00:51
 */
public class IPersistenceTest {

    private SqlSession sqlSession;

    @Before
    public void before() throws PropertyVetoException, DocumentException {
        InputStream resourcesAsStream = Resources.getResourcesAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuild().build(resourcesAsStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test() throws Exception {
        User paramsUser = new User();
        paramsUser.setId(1);
        paramsUser.setUsername("lucy");
        User user = sqlSession.selectOne("com.lagou.dao.IUserDao.findByCondition", paramsUser);
        System.out.println(user);

        List<User> userList = sqlSession.selectList("com.lagou.dao.IUserDao.findAll");
        userList.forEach(System.out::println);

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findAll();
        users.forEach(System.out::println);
        User user1 = userDao.findByCondition(paramsUser);
        System.out.println(user1);
    }

    @Test
    public void insertTest() {
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setId(10);
        user.setUsername("张三");
        user.setPassword("123");
        user.setBirthday("1996-04-03");
        int i = userDao.insertUser(user);
        System.out.println(i);
    }
}
