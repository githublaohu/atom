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

import com.lamp.atom.service.domain.CodeMode;
import com.lamp.atom.service.domain.DeployType;
import com.lamp.atom.service.domain.OperatorModel;
import com.lamp.atom.service.domain.OperatorRuntimeType;
import com.lamp.atom.service.domain.OperatorSourceType;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="OperatorEntity",description="算子类")
public class OperatorEntity extends BaseEntity {

    private static final long serialVersionUID = -3837234206057086494L;

    /**
     * 空间id
     */
    private Long spaceId;

    /**
     * 算子模板
     */
    private Long operatorTemplateId;

    /**
     * 算子名
     */
    private String operatorName;

    /**
     * 算子源id
     */
    private Long operatorSourceId;

    /**
     * 算子来源类型
     */
    private OperatorSourceType operatorSourceType;

    /**
     * 算子类型
     */
    private OperatorRuntimeType operatorRuntimeType;

    /**
     * 算子运行模式
     */
    private OperatorModel operatorModel;

    /**
     * 细化级别
     */
    private Integer level;

    /**
     * 资源账户id
     */
    private Long resourcesAccountId;

    /**
     * 加载模式
     */
    private CodeMode codeMode;

    /**
     * 加载地址
     */
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
     * 训练轮数
     */
    private Integer operatorEpoch;

    /**
     * 训练预计时长
     */
    private String operatorPlanRuntimes;

     /**
     * 训练优先级
     */
    private Integer operatorPriority;

    /**
     * 部署类型
     */
    private DeployType deployType;

}