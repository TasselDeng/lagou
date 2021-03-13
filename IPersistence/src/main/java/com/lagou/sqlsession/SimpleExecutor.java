package com.lagou.sqlsession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MapperStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * jdbc sql处理器具体实现
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 20:17
 */
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, IntrospectionException {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mapperStatement, params);
        // 5、执行SQL
        ResultSet resultSet = preparedStatement.executeQuery();
        // 6、封装结果集
        List<Object> list = new ArrayList<>();
        String resultType = mapperStatement.getResultType();
        Class<?> resultClass = getClassType(resultType);
        while (resultSet.next()) {
            // 封装对象实例
            Object resultObject = resultClass.getDeclaredConstructor().newInstance();
            // 获取元数据，即查询的数据库表列名
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段值
                Object columnValue = resultSet.getObject(columnName);
                // 使用反射或者内省，根据数据库字段名和结果集对象字段对应，完成结果集对象封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultClass);
                // 获取写方法
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(resultObject, columnValue);
            }
            list.add(resultObject);
        }

        return (List<E>) list;
    }

    @Override
    public int update(Configuration configuration, MapperStatement mapperStatement, Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mapperStatement, params);
        return preparedStatement.executeUpdate();
    }

    /**
     * 获取PreparedStatement
     *
     * @param configuration
     * @param mapperStatement
     * @param param
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private PreparedStatement getPreparedStatement(Configuration configuration, MapperStatement mapperStatement, Object... param) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 1、获取数据库连接
        Connection connection = configuration.getSource().getConnection();
        // 2、解析SQL
        String sql = mapperStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        // 3、预处理SQL，获取preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getContent());
        // 4、设置参数
        String parameterType = mapperStatement.getParameterType();
        Class<?> parameterClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            String parameterName = parameterMappingList.get(i).getContent();
            // 利用反射给字段参数设置值
            Field field = parameterClass.getDeclaredField(parameterName);
            // 暴力访问
            field.setAccessible(true);
            Object o = field.get(param[0]);
            preparedStatement.setObject(i + 1, o);
        }
        return preparedStatement;
    }

    /**
     * 反射获取类
     *
     * @param className 类包路径
     * @return
     * @throws ClassNotFoundException
     */
    private Class<?> getClassType(String className) throws ClassNotFoundException {
        if (className != null) {
            return Class.forName(className);
        }
        return null;
    }

    /**
     * 将解析的SQL信息封装进BoundSql
     *
     * @param sql 待解析的SQL
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        // 标记处理类：主要配合通用标记解析器GenericTokenParser类完成对配置⽂件等的解析⼯作，其中TokenHandler主要完成处理
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        // GenericTokenParser：通⽤的标记解析器，完成了代码⽚段中的占位符的解析，然后再根据给定的标记处理器(TokenHandler)来进⾏表达式的处理
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // 解析后的SQL语句
        String parseSql = genericTokenParser.parse(sql);
        // 解析出的参数名称
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappingList);
    }
}
