package com.lamp.atom.service.space.entity;

import lombok.Data;

@Data
public class EnvironmentEntity extends BaseEntity {

    private static final long serialVersionUID = 1725224524198493890L;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 环境类型
     */
    private String environmentType;

    /**
     * 环境名
     */
    private String environmentName;

    /**
     * 环境作用
     */
    private String environmentRole;

    /**
     * 标签
     */
    private String label;

    /**
     * 创建人ID
     */
    private Long createId;

    /**
     * 创建人名
     */
    private String createName;

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
}
