package com.lagou.sqlsession;

import com.lagou.utils.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储解析SQL后的信息
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 21:00
 */
@Data
@AllArgsConstructor
public class BoundSql {

    /**
     * 解析后可执行的SQL，即已把#{id}内容替换成?
     */
    private String content;

    /**
     * SQL解析出来的参数名称对象list
     */
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();
}
