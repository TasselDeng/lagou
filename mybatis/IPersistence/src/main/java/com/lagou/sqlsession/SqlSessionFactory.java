package com.lagou.sqlsession;

/**
 * SQL会话工厂
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 01:31
 */
public interface SqlSessionFactory {

    /**
     * 打开一个SQL会话
     *
     * @return SqlSession
     */
    SqlSession openSession();
}
