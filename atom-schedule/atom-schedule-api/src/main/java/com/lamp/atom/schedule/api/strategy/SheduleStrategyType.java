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
public enum SheduleStrategyType {
	
	DEFAULT_RONDOM("default","default","rondom"),
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
	ATOM_RPC_RONDOM("atom","rpc","total quantity"),
	ATOM_RPC_TOTAL_QUANTITY("atom","rpc","total quantity"),
	ATOM_RPC_LABEL("atom","rpc","label"),
	ATOM_RPC_REPLACE("atom","rpc","replace"),
	;
	
	private String assembly;
	
	private String strategy;
	
	private String model;
	
	
}
