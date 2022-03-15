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

import com.lamp.atom.service.domain.*;
import com.lamp.atom.service.domain.OperatorRuntimeStatus;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="OperatorEntity",description="算子类")
public class OperatorEntity extends BaseEntity {

    private static final long serialVersionUID = -3837234206057086494L;

    /**
     * 空间id
     */
    @NonNull
    private Long spaceId;

    /**
     * 算子模板
     */
    @NonNull
    private Long operatorTemplateId;

    /**
     * 算子名
     */
    private String operatorName;

    /**
     * 算子源id
     */
    @NonNull
    private Long operatorSourceId;

    /**
     * 算子来源类型
     */
    @NonNull
    private OperatorSourceType operatorSourceType;

    /**
     * 算子类型
     */
    @NonNull
    private OperatorRuntimeType operatorRuntimeType;

    /**
     * 算子运行模式
     */
    @NonNull
    private OperatorModel operatorModel;

    /**
     * 细化级别
     */
    private Integer level;

    /**
     * 资源账户id
     */
    @NonNull
    private Long resourcesAccountId;

    /**
     * 加载模式
     */
    @NonNull
    private CodeMode codeMode;

    /**
     * 加载地址
     */
    @NonNull
    private String codeAddress;

    /**
     * 代码版本
     */
    private String codeVersion;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 执行对象
     */
    private String executeObject;

    /**
     * 算子配置
     */
    private String operatorConf;

    /**
     * 算子环境配置
     */
    private String environmentConf;

    /**
     * 模型配置
     */
    private String modelConf;

    /**
     * cpu使用率
     */
    private String cpu;

    /**
     * gpu使用率
     */
    private String gpu;

    /**
     * 内存使用率
     */
    private String men;

    /**
     * 显卡使用率
     */
    private String displayCard;

    /**
     * 训练轮数
     */
    private Integer operatorEpoch;

    /**
     * 训练预计时长
     */
    private String operatorPlanRuntimes;

    /**
     * 训练状态
     */
    @NonNull
    private OperatorRuntimeStatus operatorRuntimeStatus;

    /**
     * 训练优先级
     */
    private Integer operatorPriority;

    /**
     * 部署实例
     */
    private Long caseId;

    /**
     * 部署类型
     */
    private DeployType deployType;

    public OperatorEntity(Long id) {
        super(id);
    }
}