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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 服务IP
     */
    private String serverIp;

    /**
     * 算子名
     */
    private String operatorName;

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