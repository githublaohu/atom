package com.lamp.atom.service.domain;

/**
 * 部署类型
 */
public enum DeployType {
    /**
     * 不部署
     */
    NOT_DEPLOY("不部署"),

    /**
     * 触发部署
     */
    TOUCH("触发部署"),

    /**
     * 自动化部署
     */
    AUTO("自动化部署");

    private String name;

    DeployType(String name) {
        this.name = name;
    }
}
