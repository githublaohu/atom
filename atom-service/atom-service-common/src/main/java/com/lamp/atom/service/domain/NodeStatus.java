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
 * 节点状态
 */
public enum NodeStatus {

    /**
     * 创建完成
     */
    CREATE("创建完成"),

    /**
     * 编辑中
     */
    EDITING("编辑中"),

    /**
     * 编辑完成
     */
    EDIT_FINISH("编辑完成");

    private String name;

    NodeStatus(String name) {
        this.name = name;
    }
}
