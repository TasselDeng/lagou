package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Element> insertElementList = rootElement.selectNodes("//insert");
        List<Element> updateElementList = rootElement.selectNodes("//update");
        List<Element> deleteElementList = rootElement.selectNodes("//delete");
        List<Element> elementList = Stream.of(selectElementList, insertElementList,
                updateElementList, deleteElementList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        for (Element element : elementList) {
            // 构建封装MapperStatement
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getTextTrim();
            MapperStatement mapperStatement = new MapperStatement(id, parameterType, resultType, sql);
            // 赋值进configuration
            String key = namespace + "." + id;
            configuration.getMapperStatementMap().put(key, mapperStatement);
        }
    }
}
