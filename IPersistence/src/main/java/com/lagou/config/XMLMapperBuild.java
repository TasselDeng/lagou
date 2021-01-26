package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-01-24 15:57
 */
public class XMLMapperBuild {

    private Configuration configuration;

    public XMLMapperBuild(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析mapper.xml，赋值封装到configuration
     *
     * @param is mapper.xml配置文件输入流
     */
    public void parserConfig(InputStream is) throws DocumentException {
        Document document = new SAXReader().read(is);
        // 根节点
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElementList = rootElement.selectNodes("//select");
        for (Element selectElement : selectElementList) {
            // 构建封装MapperStatement
            String id = selectElement.attributeValue("id");
            String parameterType = selectElement.attributeValue("parameterType");
            String resultType = selectElement.attributeValue("resultType");
            String sql = selectElement.getTextTrim();
            MapperStatement mapperStatement = new MapperStatement(id, parameterType, resultType, sql);
            // 赋值进configuration
            String key = namespace + "." + id;
            configuration.getMapperStatementMap().put(key, mapperStatement);
        }
    }
}
