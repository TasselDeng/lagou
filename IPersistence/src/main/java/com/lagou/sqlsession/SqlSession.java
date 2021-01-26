package com.lagou.sqlsession;

import java.sql.SQLException;
import java.util.List;

/**
 * SQL会话，执行增、删、改、查
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 19:46
 */
public interface SqlSession {

    /**
     * 查询多个结果
     *
     * @param statementId sql唯一标识
     * @param params      sql参数
     * @param <E>         E
     * @return E
     */
    <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, Exception;

    /**
     * 查询单个结果
     *
     * @param statementId sql唯一标识
     * @param params      sql参数
     * @param <T>         T
     * @return T
     */
    <T> T selectOne(String statementId, Object... params) throws SQLException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, Exception;


    /**
     * 利用JDK动态代理生成代理对象，并返回
     *
     * @param mapperClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<?> mapperClass);
}
