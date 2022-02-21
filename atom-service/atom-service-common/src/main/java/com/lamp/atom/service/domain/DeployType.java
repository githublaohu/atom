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
    TOUCH_DEPLOY("触发部署"),

    /**
     * 灰度部署
     */
    GREY_DEPLOY("灰度部署"),

    /**
     * 自动化部署
     */
    AUTO_DEPLOY("自动化部署");

    private String name;

    DeployType(String name) {
        this.name = name;
    }
}
