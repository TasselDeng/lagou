package com.lagou.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 订单实体
 *
 * @author ying
 * @version 1.0
 * @date 2021-02-12 22:38
 */
@Data
public class Order {

    private Integer id;

    private Date ordertime;

    private Double total;

    private User user;
}
