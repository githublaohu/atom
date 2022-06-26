package com.lamp.atom.schedule.python.operator.rpc;

import org.junit.Test;

import com.alibaba.nacos.api.exception.NacosException;
import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.config.OperatorScheduleRpcConfig;
import com.lamp.atom.schedule.api.strategy.ScheduleStrategyType;
import com.lamp.atom.schedule.api.strategy.Strategy;
import com.lamp.atom.schedule.python.operator.CreateOperator;

public class TestOperatorRpcSchedule {

	
	
	@Test
	public void test() throws NacosException {
		OperatorScheduleRpcConfig operatorScheduleRpcConfig = new OperatorScheduleRpcConfig();
		operatorScheduleRpcConfig.setServerAddr("127.0.0.1:8848");
		operatorScheduleRpcConfig.setNamespace("atom-dev");
		OperatorRpcSchedule operatorRpcSchedule = new OperatorRpcSchedule(operatorScheduleRpcConfig);
		operatorRpcSchedule.hashCode();
		Schedule schedule = new Schedule();
		Strategy strategy = new Strategy();
		strategy.setScheduleStrategyType(ScheduleStrategyType.DEFAULT_TOTAL_QUANTITY);
		schedule.setStrategy(strategy);
		CreateOperator createOperator = new CreateOperator();
		schedule.setObject(createOperator);
		operatorRpcSchedule.createOperators(schedule);
	}
}
