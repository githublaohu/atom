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
package com.lamp.atom.schedule.python.operator.kubernetes;

import java.util.Objects;

import com.lamp.atom.schedule.api.AtomOperatorShedule;
import com.lamp.atom.schedule.api.AtomServiceShedule;
import com.lamp.atom.schedule.api.Shedule;
import com.lamp.atom.schedule.api.config.OperatorShedeleKubernetesConfig;

import io.fabric8.kubernetes.api.model.batch.v1.Job;
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
		Job job = client.batch().v1().jobs().inNamespace(operatorKubernetesConfig.getNamespace())
				.createOrReplace(operatorKubernetesBuilder.getJob());
		job.getKind();
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
