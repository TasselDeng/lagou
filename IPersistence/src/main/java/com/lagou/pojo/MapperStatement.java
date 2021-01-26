package com.lagou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * mapper实体
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-24 01:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapperStatement {

    private String id;

    private String parameterType;

    private String resultType;

    private String sql;
}
