package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author ying
 * @version 1.0
 * @date 2021-01-24 01:33
 */
public class XMLConfigBuild {

    private Configuration configuration;

    public XMLConfigBuild() {
        configuration = new Configuration();
    }

    /**
     * 使用dom4j对配置文件输入流解析封装成Configuration
     *
     * @param is 配置文件输入流
     * @return Configuration
     */
    public Configuration parserConfig(InputStream is) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(is);
        Element rootElement = document.getRootElement();
        // 获取property标签
        List<Element> propertyElementList = rootElement.selectNodes("//property");
        // 将property标签数据填充进properties
        Properties properties = new Properties();
        for (Element propertyElement : propertyElementList) {
            String name = propertyElement.attributeValue("name");
            String value = propertyElement.attributeValue("value");
            properties.setProperty(name, value);
        }
        // c3p0数据库连接池，设置参数
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setUser(properties.getProperty("user"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        // 封装到configuration中
        configuration.setSource(comboPooledDataSource);

        // mapper.xml
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element mapperElement : mapperList) {
            String path = mapperElement.attributeValue("resource");
            InputStream resourcesAsStream = Resources.getResourcesAsStream(path);
            XMLMapperBuild xmlMapperBuild = new XMLMapperBuild(configuration);
            xmlMapperBuild.parserConfig(resourcesAsStream);
        }
        return configuration;
    }
}
