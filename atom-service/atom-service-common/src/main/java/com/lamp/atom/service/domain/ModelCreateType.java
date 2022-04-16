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
 * 模型创建类型
 */
public enum ModelCreateType {

    /**
     * 数据
     */
    DATA,

    /**
     * 训练
     */
    TRAIN,

    /**
     * 推理
     */
    REASON,

    /**
     * 特征
     */
    FEATURE,

    /**
     * 上传
     */
    UPLOAD
}
