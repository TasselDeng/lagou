package com.lagou.edu.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ying
 * @version 1.0
 * @date 2021-05-06 20:52
 */
@Data
@Entity  // 标记为一个实体，能够被JPA所识别
@Table(name = "tb_resume")  // 实体映射的表
public class Resume {

    /**
     * 主键生成策略
     * GenerationType.IDENTITY:依赖数据库中主键自增功能  Mysql
     * GenerationType.SEQUENCE:依靠序列来产生主键     Oracle
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id  // 标记为id
    @Column(name = "id")  // 表映射的字段
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;
}
