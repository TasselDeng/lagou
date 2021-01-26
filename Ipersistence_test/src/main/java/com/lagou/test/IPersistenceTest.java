package com.lagou.test;

import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlsession.SqlSession;
import com.lagou.sqlsession.SqlSessionFactory;
import com.lagou.sqlsession.SqlSessionFactoryBuild;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-01-24 00:51
 */
public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourcesAsStream = Resources.getResourcesAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuild().build(resourcesAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User paramsUser = new User();
        paramsUser.setId(1);
        paramsUser.setUsername("lucy");
        User user = sqlSession.selectOne("User.selectOne", paramsUser);
        System.out.println(user);

        List<User> userList = sqlSession.selectList("User.selectList");
        userList.forEach(System.out::print);
    }
}
