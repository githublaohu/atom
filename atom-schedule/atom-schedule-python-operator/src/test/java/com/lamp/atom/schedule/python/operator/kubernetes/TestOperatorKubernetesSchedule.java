package com.lamp.atom.schedule.python.operator.kubernetes;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.lamp.atom.schedule.api.Schedule;
import org.junit.Test;

import com.lamp.atom.schedule.api.config.OperatorShedeleKubernetesConfig;

public class TestOperatorKubernetesSchedule {

	Schedule schedule = new Schedule();

	OperatorShedeleKubernetesConfig operatorShedeleKubernetesConfig;

	private String nacosAddress = "172.19.75.218:8848";

	{
		schedule.setNodeName("test");
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

		operatorShedeleKubernetesConfig = new OperatorShedeleKubernetesConfig();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("kubernetesConfig.yaml");
			byte[] data = new byte[inputStream.available()];
			inputStream.read(data);
			operatorShedeleKubernetesConfig.setConfigYaml(new String(data));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testCreateService() throws Exception {
		OperatorKubernetesSchedule kubernetesSchedule = new OperatorKubernetesSchedule(operatorShedeleKubernetesConfig);

		kubernetesSchedule.createService(schedule);
	}
	
	@Test
	public void testCreateOperators() throws Exception {
		OperatorKubernetesSchedule kubernetesSchedule = new OperatorKubernetesSchedule(operatorShedeleKubernetesConfig);

		kubernetesSchedule.createOperators(schedule);
	}
}
