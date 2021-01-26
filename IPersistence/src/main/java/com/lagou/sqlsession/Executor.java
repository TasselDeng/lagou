package com.lagou.sqlsession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MapperStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * jdbc sql处理器接口
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 20:12
 */
public interface Executor {

    /**
     * 用jdbc执行查询语句
     *
     * @param configuration   数据库配置信息
     * @param mapperStatement SQL信息
     * @param params          SQL参数
     * @param <E>             E
     * @return List<E>
     */
    <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, IntrospectionException;
}
