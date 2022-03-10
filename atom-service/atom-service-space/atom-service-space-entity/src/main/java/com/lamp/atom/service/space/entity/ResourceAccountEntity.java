package com.lamp.atom.service.space.entity;

import lombok.Data;

@Data
public class ResourceAccountEntity extends BaseEntity {

    private static final long serialVersionUID = -9189715252804021969L;

    /**
     * 环境ID
     */
    private Long environmentId;

    /**
     * 环境名字
     */
    private String environmentName;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 密码类型 密码，秘钥，免密
     */
    private String passwordType;

    /**
     * 账户密码
     */
    private String password;

    /**
     * 拥有人ID
     */
    private Long ownerId;

    /**
     * 拥有人名
     */
    private String ownerName;

    /**
     * 拥有类型
     */
    private String ownerType;

    /**
     * 状态
     */
    private String status;
}
