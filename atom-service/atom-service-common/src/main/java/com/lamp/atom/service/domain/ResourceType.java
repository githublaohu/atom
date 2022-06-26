package com.lamp.atom.service.domain;

public enum ResourceType {

    /**
     * 算子
     */
    OPERATOR("算子"),

    /**
     * 数据源
     */
    DATASOURCE("数据源"),

    /**
     * 模型
     */
    MODEL("模型"),

    /**
     * 连接
     */
    CONNECTION("连接"),

    /**
     * 节点
     */
    NODE("节点"),

    /**
     * 组织
     */
    ORGANIZATION("组织"),

    /**
     * 服务信息
     */
    SERVICE_INFO("服务信息"),

    /**
     * 服务配置最大值
     */
    MAX_SERVICE_INFO("服务配置最大值"),

    /**
     * 服务配置最小值
     */
    MIN_SERVICE_INFO("服务配置最小值"),

    /**
     * 部署
     */
    DEPLOY("部署");

    public String name;

    ResourceType(String name) {
        this.name = name;
    }
}
