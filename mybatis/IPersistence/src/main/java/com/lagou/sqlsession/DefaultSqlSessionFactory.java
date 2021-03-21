package com.lagou.sqlsession;

import com.lagou.pojo.Configuration;

/**
 * 默认Sql会话工厂
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 19:49
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
