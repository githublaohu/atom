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
package com.lamp.atom.schedule.core;

import com.lamp.atom.schedule.api.AtomOperatorShedule;
import com.lamp.atom.schedule.api.AtomServiceShedule;
import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.config.OperatorScheduleConfig;
import com.lamp.atom.schedule.python.operator.kubernetes.OperatorKubernetesSchedule;
import com.lamp.atom.schedule.python.operator.rpc.OperatorRpcSchedule;
import com.lamp.atom.service.domain.OperatorRuntimeType;

import java.util.HashMap;
import java.util.Map;

/**
 * 上层决定调用那个
 * @author laohu
 *
 */
public class AtomScheduleService  implements AtomOperatorShedule, AtomServiceShedule {

	
	private OperatorKubernetesSchedule kubernetesSchedule;
	
	private OperatorRpcSchedule rpcSchedule;
	
	private OperatorScheduleConfig operatorScheduleConfig;


	private Map<OperatorRuntimeType,AtomOperatorShedule> atomOperatorScheduleMap = new HashMap<>();

	public AtomScheduleService(OperatorScheduleConfig operatorScheduleConfig) throws Exception {
		this.operatorScheduleConfig = operatorScheduleConfig;
		if(operatorScheduleConfig.getOperatorKubernetesConfig().isUser()) {
			kubernetesSchedule = new OperatorKubernetesSchedule(this.operatorScheduleConfig.getOperatorKubernetesConfig());
		}
		rpcSchedule = new OperatorRpcSchedule(this.operatorScheduleConfig.getOperatorScheduleRpcConfig());

		atomOperatorScheduleMap.put(OperatorRuntimeType.TRAIN,kubernetesSchedule);
		atomOperatorScheduleMap.put(OperatorRuntimeType.REASONING,rpcSchedule);

	}
	
	
	@Override
	public void createService(Schedule schedule) {
		kubernetesSchedule.createService(schedule);
	}

	@Override
	public void closeService(Schedule schedule) {
		kubernetesSchedule.closeService(schedule);
	}

	@Override
	public void createOperators(Schedule schedule) {
		OperatorRuntimeType operatorRuntimeType = schedule.getOperatorRuntimeType();
		// 1、训练 =》 k8s调度；2、推理 =》 RPC调度
		atomOperatorScheduleMap.get(operatorRuntimeType).createOperators(schedule);
	}

	@Override
	public void startOperators(Schedule schedule) {
		rpcSchedule.startOperators(schedule);
	}

	@Override
	public void suspendOperators(Schedule schedule) {
		rpcSchedule.suspendOperators(schedule);
	}

	@Override
	public void uninstallOperators(Schedule schedule) {
		rpcSchedule.uninstallOperators(schedule);
	}

}
