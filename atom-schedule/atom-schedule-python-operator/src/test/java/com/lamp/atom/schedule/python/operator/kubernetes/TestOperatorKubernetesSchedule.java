package com.lamp.atom.schedule.python.operator.kubernetes;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lamp.atom.schedule.api.Shedule;
import com.lamp.atom.schedule.api.config.OperatorShedeleKubernetesConfig;
import com.lamp.atom.schedule.api.deploy.Deploy;

public class TestOperatorKubernetesSchedule {

	Shedule shedule = new Shedule();

	OperatorShedeleKubernetesConfig operatorShedeleKubernetesConfig;

	private String nacosAddress = "172.19.75.218:8848";

	{
		shedule.setNoteName("test");
		Map<String, String> labels = new HashMap<>();
		labels.put("test", "test");
		shedule.setLabels(labels);
		Map<String, String> hardwareConfig = new HashMap<>();
		hardwareConfig.put("cpu", "2");
		hardwareConfig.put("memory", "1Gi");
		shedule.setHardwareConfig(hardwareConfig);
		Map<String, String> envs = new HashMap<>();
		envs.put("nacos_config", "{'nacos_address':'127.0.0.1','nacos_namespace':'atom'}");
		shedule.setEnvs(envs);

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
		Deploy deploy = new Deploy();
		deploy.setCount(4);
		shedule.setDeploy(deploy);
		kubernetesSchedule.createService(shedule);
	}
	
	@Test
	public void testCreateOperators() throws Exception {
		OperatorKubernetesSchedule kubernetesSchedule = new OperatorKubernetesSchedule(operatorShedeleKubernetesConfig);

		kubernetesSchedule.createOperators(shedule);
	}
}
