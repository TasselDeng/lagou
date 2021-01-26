package com.lagou.sqlsession;

import com.lagou.config.XMLConfigBuild;
import com.lagou.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * 创建SqlSession工厂
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 01:28
 */
public class SqlSessionFactoryBuild {

    public SqlSessionFactory build(InputStream is) throws PropertyVetoException, DocumentException {
        // 1、使用dom4j解析配置文件，将配置文件内容封装到Configuration中
        XMLConfigBuild xmlConfigBuild = new XMLConfigBuild();
        Configuration configuration = xmlConfigBuild.parserConfig(is);
        // 2、创建SQL会话工厂
        return new DefaultSqlSessionFactory(configuration);
    }
}
