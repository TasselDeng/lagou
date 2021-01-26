package com.lagou.sqlsession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MapperStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * 默认SQL会话，执行增删改查
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 19:47
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Executor executor = new SimpleExecutor();

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        return executor.query(configuration, mapperStatement, params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objectList = selectList(statementId, params);
        if (objectList.size() == 1) {
            return (T) objectList.get(0);
        } else if (objectList.size() > 1) {
            throw new RuntimeException("返回结果有多个");
        }
        return null;
    }
}
