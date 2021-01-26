package com.lagou.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 解析SQL出来参数名称的封装
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 21:00
 */
@Data
@AllArgsConstructor
public class ParameterMapping {

    /**
     * 解析SQL出来的参数名称
     */
    private String content;
}
