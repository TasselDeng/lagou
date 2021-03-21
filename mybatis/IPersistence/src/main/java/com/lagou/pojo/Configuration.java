package com.lagou.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库配置信息实体
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 01:16
 */
@Data
public class Configuration {

    private DataSource source;

    /**
     * key:mapperStatementId(namespace.id)，value：MapperStatement
     */
    private Map<String, MapperStatement> mapperStatementMap = new HashMap<>();
}
