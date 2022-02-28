package com.lamp.atom.schedule.python.operator.rpc;

import org.junit.Test;

import com.alibaba.nacos.api.exception.NacosException;
import com.lamp.atom.schedule.api.Shedule;
import com.lamp.atom.schedule.api.config.OperatorShedeleRpcConfig;
import com.lamp.atom.schedule.api.strategy.SheduleStrategyType;
import com.lamp.atom.schedule.api.strategy.Strategy;
import com.lamp.atom.schedule.python.operator.CreateOperator;

public class TestOperatorRpcSchedule {

	
	
	@Test
	public void test() throws NacosException {
		OperatorShedeleRpcConfig operatorShedeleRpcConfig = new OperatorShedeleRpcConfig();
		operatorShedeleRpcConfig.setServerAddr("127.0.0.1:8848");
		operatorShedeleRpcConfig.setNamespace("atom-dev");
		OperatorRpcSchedule operatorRpcSchedule = new OperatorRpcSchedule(operatorShedeleRpcConfig);
		operatorRpcSchedule.hashCode();
		Shedule shedule = new Shedule();
		Strategy strategy = new Strategy();
		strategy.setSheduleStrategyType(SheduleStrategyType.DEFAULT_TOTAL_QUANTITY);
		shedule.setStrategy(strategy);
		CreateOperator createOperator = new CreateOperator();
		shedule.setObject(createOperator);
		operatorRpcSchedule.createOperators(shedule);
	}
}
