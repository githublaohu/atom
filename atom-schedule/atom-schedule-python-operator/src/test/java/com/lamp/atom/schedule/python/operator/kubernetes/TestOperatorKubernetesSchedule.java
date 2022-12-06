package com.lamp.atom.schedule.python.operator.kubernetes;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.config.OperatorScheduleKubernetesConfig;
import com.lamp.atom.schedule.api.deploy.Deploy;
import com.lamp.atom.schedule.api.strategy.Strategy;

public class TestOperatorKubernetesSchedule {

	Schedule schedule = new Schedule();

	OperatorScheduleKubernetesConfig operatorScheduleKubernetesConfig;
	
	Map<String, String> envs;

	{
		Strategy strategy = new Strategy();
		strategy.setWorksNum(6);
		schedule.setStrategy(strategy);
		
		schedule.setNodeName("test");

		Deploy deploy = new Deploy();
		deploy.setCount(1);
		schedule.setDeploy(deploy);

		Map<String, String> labels = new HashMap<>();
		labels.put("test", "test");
		schedule.setLabels(labels);

		Map<String, String> hardwareConfig = new HashMap<>();
		hardwareConfig.put("cpu", "2");
		hardwareConfig.put("memory", "1Gi");
		schedule.setHardwareConfig(hardwareConfig);

		envs = new HashMap<>();
		//envs.put("nacos_config", "{'nacos_address':'127.0.0.1','nacos_namespace':'atom'}");
		schedule.setEnvs(envs);
		
		operatorScheduleKubernetesConfig = new OperatorScheduleKubernetesConfig();
		operatorScheduleKubernetesConfig.setCpuContainerName("githublaohu/atom-base-cpu:tf1.15-cpu-0.0.9");
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("kubernetesConfig.yaml");
			byte[] data = new byte[inputStream.available()];
			inputStream.read(data);
			operatorScheduleKubernetesConfig.setConfigYaml(new String(data));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testCreateService() throws Exception {
		OperatorKubernetesSchedule kubernetesSchedule = new OperatorKubernetesSchedule(operatorScheduleKubernetesConfig);
		Deploy deploy = new Deploy();
		deploy.setCount(4);
		schedule.setDeploy(deploy);
		kubernetesSchedule.createService(schedule);
	}
	
	@Test
	public void testCreateOperators() throws Exception {
		envs.put("test_model","True");
		
		OperatorKubernetesSchedule kubernetesSchedule = new OperatorKubernetesSchedule(operatorScheduleKubernetesConfig);
		kubernetesSchedule.createOperators(schedule);
		
	}
	
	@Test
	public void testUninstallOperators() throws Exception{
		OperatorKubernetesSchedule kubernetesSchedule = new OperatorKubernetesSchedule(operatorScheduleKubernetesConfig);
		
		schedule.setNodeName("atom-runtime-session-test--1-5485z");
		kubernetesSchedule.uninstallOperators(schedule);
	}
}
