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
package com.lamp.atom.schedule.api.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 不管什么都有
 * 1. 随机
 * 2. 全量
 * 3. 指定标签
 * 4. 指定IP
 * 
 * 2. 多个
 *  2. 排队
 *  
 * @author laohu
 *
 */
@Getter
@AllArgsConstructor
public enum ScheduleStrategyType {
	
	DEFAULT_RONDOM("default","default","random"),
	DEFAULT_TOTAL_QUANTITY("default","default","total quantity"),
	DEFAULT_LABEL("default","default","label"),
	DEFAULT_REPLACE("default","default","replace"),
	DEFAULT_BATCH("default","default","batch"),
	KUBERNETES_SIMPLE("kubernetes","simple",""),
	KUBERNETES_SIMPLE_NODE_SELECTOR("kubernetes","simple","nodeSelector"),
	KUBERNETES_SIMPLE_NODE_AFFINITY("kubernetes","simple","nodeAffinity"),
	KUBERNETES_SIMPLE_TAINTS("kubernetes","simple","taints"),
	KUBERNETES_SIMPLE_DAEMONSET("kubernetes","simple","daemonset"),
	KUBERNETES_BACH("kubernetes","bach",""),
	KUBERNETES_BACH_JOB_TEMPLATE("kubernetes","bach","job template expansion"),
	KUBERNETES_BACH_QUEUE_WITH_POD_PER_WORK_TIME("kubernetes","bach","queue with pod work item"),
	KUBERNETES_BACH_QUEUE_WITH_VARIABLE_POD_COUNT("kubernetes","bach","queue with variable pod count"),
	KUBERNETES_CRON("kubernetes","cron",""),
	ATOM_RPC_RANDOM("atom","rpc","total quantity"),
	ATOM_RPC_TOTAL_QUANTITY("atom","rpc","total quantity"),
	ATOM_RPC_LABEL("atom","rpc","label"),
	ATOM_RPC_REPLACE("atom","rpc","replace"),
	;
	
	private String assembly;
	
	private String strategy;
	
	private String model;
	
	
}
