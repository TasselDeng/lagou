package com.lagou.sqlsession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MapperStatement;

import java.lang.reflect.*;
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

    @Override
    public int insert(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        return executor.update(configuration, mapperStatement, params);
    }

    @Override
    public int delete(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        return executor.update(configuration, mapperStatement, params);
    }

    @Override
    public int update(String statementId, Object... params) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        return executor.update(configuration, mapperStatement, params);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object o = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            // proxy当前代理对象的应用，method当前被调用方法的引用，args传递的参数
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层还是执行JDBC代码，根据不同情况调用selectList或selectOne
                // 准备参数 statementId ：SQL语句的唯一标识，namespace.id
                // 因为这边获取不到mapper.xml的namespace.id，所以将mapper.xml的namespace配成对应接口全路径名，id为方法名
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
                // 调用方法返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了泛型类型参数化，声明类型中带有“<>”的都是参数化类型
                switch (mapperStatement.getTagEnum()) {
                    case SELECT:
                        if (genericReturnType instanceof ParameterizedType) {
                            return selectList(statementId, args);
                        }
                        return selectOne(statementId, args);
                    case UPDATE:
                        return update(statementId, args);
                    case INSERT:
                        return insert(statementId, args);
                    case DELETE:
                        return delete(statementId, args);
                    default:
                        throw new RuntimeException("标签类型错误！");
                }
            }
        });

        return (T) o;
    }
}
