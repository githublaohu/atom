package com.lamp.atom.service.operator.entity;

import com.lamp.atom.service.domain.CodeMode;
import com.lamp.atom.service.domain.OperatorModel;
import com.lamp.atom.service.domain.OperatorRuntimeType;
import com.lamp.atom.service.domain.OperatorSourceType;
import com.lamp.atom.service.domain.OperatorStatus;
import com.lamp.atom.service.domain.DeployType;
import lombok.Data;

@Data
public class OperatorEntity extends BaseEntity {

    private static final long serialVersionUID = -3837234206057086494L;

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
    private String operatorSourceId;

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
    private OperatorStatus operatorStatus;

    /**
     * 训练优先级
     */
    private Integer operatorPriority;

    /**
     * 部署类型
     */
    private DeployType deployType;
}