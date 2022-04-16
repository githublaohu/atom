package com.lamp.atom.schedule.python.operator.kubernetes;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.deploy.Deploy;
import org.junit.Test;

import com.lamp.atom.schedule.api.config.OperatorScheduleKubernetesConfig;

public class TestOperatorKubernetesSchedule {

	Schedule schedule = new Schedule();

	OperatorScheduleKubernetesConfig operatorScheduleKubernetesConfig;

	private String nacosAddress = "124.223.198.143:8848";

	{
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

		Map<String, String> envs = new HashMap<>();
		envs.put("nacos_config", "{'nacos_address':'127.0.0.1','nacos_namespace':'atom'}");
		schedule.setEnvs(envs);

		operatorScheduleKubernetesConfig = new OperatorScheduleKubernetesConfig();
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

		kubernetesSchedule.createService(schedule);
	}
	
	@Test
	public void testCreateOperators() throws Exception {
		OperatorKubernetesSchedule kubernetesSchedule = new OperatorKubernetesSchedule(operatorScheduleKubernetesConfig);

		kubernetesSchedule.createOperators(schedule);
	}
}
