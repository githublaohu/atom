package com.lamp.atom.service.operator.entity;

import com.lamp.atom.service.domain.ModelCreateType;
import com.lamp.atom.service.domain.ModelTechnologyType;
import com.lamp.atom.service.domain.ModelType;
import com.lamp.atom.service.domain.OperatorResult;
import lombok.Data;

@Data
public class ModelEntity extends BaseEntity {

    private static final long serialVersionUID = 4378966092513467540L;

    /**
     * 空间id
     */
    private Long spaceId;

    /**
     * 空间名
     */
    private String spaceName;

    /**
     * 空间别名
     */
    private String spaceAlias;

    /**
     * 场景id
     */
    private Long sceneId;

    /**
     * 场景名
     */
    private String sceneName;

    /**
     * 场景别名
     */
    private String sceneAlias;

    /**
     * 实验id
     */
    private Long experimentId;

    /**
     * 实验名
     */
    private String experimentName;

    /**
     * 实验别名
     */
    private String experimentAlias;

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
     * 训练id
     */
    private Long operatorId;

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