/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
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
