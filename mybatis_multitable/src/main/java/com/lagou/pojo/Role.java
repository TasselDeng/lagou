package com.lagou.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ying
 * @version 1.0
 * @date 2021-02-13 19:45
 */
@Data
@Table(name = "sys_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rolename")
    private String roleName;

    @Column(name = "roleDesc")
    private String roleDesc;
}
