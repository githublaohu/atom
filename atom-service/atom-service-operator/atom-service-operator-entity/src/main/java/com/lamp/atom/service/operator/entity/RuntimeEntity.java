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

import java.util.Date;

import com.lamp.atom.service.domain.CaseSourceType;
import com.lamp.atom.service.domain.OperatorRuntimeStatus;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="RuntimeEntity",description="运行实例类")
public class RuntimeEntity extends BaseEntity {

    private static final long serialVersionUID = -2237946472510786359L;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 来源类型
     */
    private CaseSourceType caseSourceType;

    /**
     * 服务配置ID
     */
    private Long sourceId;

    /**
     * 节点ID
     */
    private Long nodeId;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 服务器端口
     */
    private String serverPort;

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
    private OperatorRuntimeStatus operatorRuntimeStatus;

    /**
     * 服务标签
     */
    private String label;

    /**
     * 启动人ID
     */
    private Integer startId;

    /**
     * 启动人名
     */
    private String startName;

    /**
     * 关闭人ID
     */
    private Integer endId;

    /**
     * 关闭人名
     */
    private String endName;

    public RuntimeEntity(Long id) {
        super(id);
    }
}