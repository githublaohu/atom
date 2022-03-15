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
package com.lamp.atom.service.operator.entity;

import com.lamp.atom.service.domain.ModelCreateType;
import com.lamp.atom.service.domain.ModelTechnologyType;
import com.lamp.atom.service.domain.ModelType;
import com.lamp.atom.service.domain.OperatorResult;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ModelEntity",description="模型类")
public class ModelEntity extends BaseEntity {

    private static final long serialVersionUID = 4378966092513467540L;

    /**
     * 空间id
     */
    private Long spaceId;

    /**
     * 节点id
     */
    private Long nodeId;

    /**
     * 运行实例id
     */
    private Long runtimeId;

    /**
     * 训练id
     */
    private Long operatorId;

    /**
     * 模型创建类型
     */
    private ModelCreateType modelCreateType;

    /**
     * 模型名
     */
    private String modelName;

    /**
     * 模型版本名
     */
    private String modelVersion;

    /**
     * 模型打分
     */
    private Long modelScore;

    /**
     * 模型类型
     */
    private ModelType modelType;

    /**
     * 模型工程类型
     */
    private ModelTechnologyType modelTechnologyType;

    /**
     * 模型地址
     */
    private String modelAddress;

    /**
     * 模型状态
     */
    private String modelStatus;

    /**
     * 连接id
     */
    private Long connectId;

    /**
     * 连接状态
     */
    private String connectStatus;

    /**
     * 训练结果
     */
    private OperatorResult operatorResult;

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 资源值
     */
    private String resourceValue;

    /**
     * 资源大小
     */
    private String resourceSize;

    /**
     * 产生方式
     */
    private String produceWay;

}
