package com.lamp.atom.service.operator.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaseEntity extends BaseEntity {

    private static final long serialVersionUID = -2237946472510786359L;

    /**
     * 实例名
     */
    private String caseName;

    /**
     * 算子id
     */
    private Long operatorId;

    /**
     * 算子名
     */
    private String operatorName;

    /**
     -
    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 预计开始时间
     */
    private Date estimateStartTime;

    /**
     * 预计结束时间
     */
    private Date estimateEndTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 序列
     */
    private String sequence;

    /**
     * 推理数据条数
     */
    private Integer reasonDataNum;

    /**
     * 数据流量总数
     */
    private Integer dataFlowNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 状态：0正常1删除
     */
    private Integer deleteFlag;
}