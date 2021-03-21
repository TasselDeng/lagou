package com.lagou.mapper;

import com.lagou.pojo.Order;

import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-02-12 22:43
 */
public interface IOrderMapper {

    List<Order> findAll();
}
