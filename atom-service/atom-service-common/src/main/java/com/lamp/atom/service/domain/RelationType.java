package com.lamp.atom.service.domain;

public enum RelationType {

    /**
     * 节点关系
     */
    NODE_RELATION("节点关系"),

    /**
     * 资源关系
     */
    RESOURCE_RELATION("资源关系"),

    /**
     * 配置关系
     */
    CONFIG_RELATION("配置关系"),

    /**
     * 训练与推理关系
     */

    ;

    public String name;

    RelationType(String name) {
        this.name = name;
    }
}
