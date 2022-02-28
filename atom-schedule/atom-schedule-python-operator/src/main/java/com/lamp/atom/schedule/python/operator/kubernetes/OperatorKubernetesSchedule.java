package com.lamp.atom.schedule.python.operator.kubernetes;

import java.util.Objects;

import com.lamp.atom.schedule.api.AtomOperatorShedule;
import com.lamp.atom.schedule.api.AtomServiceShedule;
import com.lamp.atom.schedule.api.Shedule;
import com.lamp.atom.schedule.api.config.OperatorShedeleKubernetesConfig;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * 
 * 
 * 普通job 定时job 批量job Resource cpu:2 nvidia.com/gpu: 2
 * 
 * @author laohu
 *
 */
public class OperatorKubernetesSchedule implements AtomOperatorShedule, AtomServiceShedule {

	private KubernetesClient client;

	private OperatorShedeleKubernetesConfig operatorKubernetesConfig;

	public  OperatorKubernetesSchedule(OperatorShedeleKubernetesConfig operatorKubernetesConfig) throws Exception {
		this.operatorKubernetesConfig = operatorKubernetesConfig;
		if (Objects.nonNull(operatorKubernetesConfig.getMasterUrl())) {
			client = new DefaultKubernetesClient(operatorKubernetesConfig.getMasterUrl());
		} else if (Objects.nonNull(operatorKubernetesConfig.getConfigYaml())) {
			Config config = Config.fromKubeconfig(operatorKubernetesConfig.getConfigYaml());
			client = new DefaultKubernetesClient(config);
		}
	}

	@Override
	public void createService(Shedule shedule) {
		StandaloneOperatorKubernetesBuilder operatorKubernetesBuilder = new StandaloneOperatorKubernetesBuilder();
		operatorKubernetesBuilder.setShedule(shedule);
		operatorKubernetesBuilder.setOperatorKubernetesConfig(operatorKubernetesConfig);
		client.apps().deployments().inNamespace(operatorKubernetesConfig.getNamespace())
				.createOrReplace(operatorKubernetesBuilder.getDeployment());
	}

	@Override
	public void closeService(Shedule shedule) {

	}

	@Override
	public void createOperators(Shedule shedule) {
		SessionOperatorKubernetesBuilder operatorKubernetesBuilder = new SessionOperatorKubernetesBuilder();
		operatorKubernetesBuilder.setShedule(shedule);
		operatorKubernetesBuilder.setOperatorKubernetesConfig(operatorKubernetesConfig);
		client.batch().v1().jobs().inNamespace(operatorKubernetesConfig.getNamespace())
				.createOrReplace(operatorKubernetesBuilder.getJob());
	}

	@Override
	public void startOperators(Shedule shedule) {

	}

	@Override
	public void suspendOperators(Shedule shedule) {

	}

	@Override
	public void uninstallPperators(Shedule shedule) {
		this.closeService(shedule);

	}
}
