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
import com.lamp.atom.schedule.api.Shedule;
import com.lamp.atom.schedule.api.config.OperatorSheduleConfig;
import com.lamp.atom.schedule.python.operator.kubernetes.OperatorKubernetesSchedule;
import com.lamp.atom.schedule.python.operator.rpc.OperatorRpcSchedule;

/**
 * 上层决定调用那个
 * @author laohu
 *
 */
public class AtomScheduleService  implements AtomOperatorShedule, AtomServiceShedule {

	
	private OperatorKubernetesSchedule kubernetesSchedule;
	
	private OperatorRpcSchedule rpcSchedule;
	
	private OperatorSheduleConfig operatorSheduleConfig;
	
	public AtomScheduleService(OperatorSheduleConfig operatorSheduleConfig) throws Exception {
		this.operatorSheduleConfig = operatorSheduleConfig;
		kubernetesSchedule = new OperatorKubernetesSchedule(this.operatorSheduleConfig.getOperatorKubernetesConfig());
		rpcSchedule = new OperatorRpcSchedule(this.operatorSheduleConfig.getOperatorShedeleRpcConfig());
	}
	
	
	@Override
	public void createService(Shedule shedule) {
		kubernetesSchedule.createService(shedule);
	}

	@Override
	public void closeService(Shedule shedule) {
		kubernetesSchedule.closeService(shedule);
	}

	@Override
	public void createOperators(Shedule shedule) {
		rpcSchedule.createOperators(shedule);
	}

	@Override
	public void startOperators(Shedule shedule) {
		rpcSchedule.startOperators(shedule);
	}

	@Override
	public void suspendOperators(Shedule shedule) {
		rpcSchedule.suspendOperators(shedule);
	}

	@Override
	public void uninstallPperators(Shedule shedule) {
		rpcSchedule.uninstallPperators(shedule);
	}

}
