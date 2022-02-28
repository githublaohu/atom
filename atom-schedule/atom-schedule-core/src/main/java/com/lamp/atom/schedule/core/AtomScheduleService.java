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
