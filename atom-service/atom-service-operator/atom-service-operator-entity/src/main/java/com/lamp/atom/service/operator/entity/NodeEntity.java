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

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "NodeEntity", description = "节点类")
public class NodeEntity extends BaseEntity {

	private static final long serialVersionUID = -2786281072838530374L;

	/**
	 * 空间id
	 */
	private Long spaceId;

	/**
	 * 节点模板
	 */
	private Long nodeTemplateId;

	/**
	 * 节点名
	 */
	private String nodeName;

	/**
	 * operator：算子来源类型
	 */
	private OperatorSourceType operatorSourceType;

	/**
	 * operator：算子类型
	 */
	private OperatorRuntimeType operatorRuntimeType;

	/**
	 * 算子运行模式
	 */
	private NodeModel nodeModel;

	/**
	 * 执行次数
	 */
	private Integer nodeEpoch;

	/**
	 * 训练预计时长
	 */
	private String nodePlanRuntimes;

	/**
	 * 节点状态
	 */
	private NodeStatus nodeStatus;

	/**
	 * 训练优先级
	 */
	private Integer operatorPriority;

}
