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
import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.config.OperatorScheduleKubernetesConfig;

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

	private OperatorScheduleKubernetesConfig operatorKubernetesConfig;

	public OperatorKubernetesSchedule(OperatorScheduleKubernetesConfig operatorKubernetesConfig) throws Exception {
		this.operatorKubernetesConfig = operatorKubernetesConfig;
		if (Objects.nonNull(operatorKubernetesConfig.getMasterUrl())) {
			client = new DefaultKubernetesClient(operatorKubernetesConfig.getMasterUrl());
		} else if (Objects.nonNull(operatorKubernetesConfig.getConfigYaml())) {
			Config config = Config.fromKubeconfig(operatorKubernetesConfig.getConfigYaml());
			client = new DefaultKubernetesClient(config);
		}
	}

	@Override
	public void createService(Schedule schedule) {
		StandaloneOperatorKubernetesBuilder operatorKubernetesBuilder = new StandaloneOperatorKubernetesBuilder();
		operatorKubernetesBuilder.setSchedule(schedule);
		operatorKubernetesBuilder.setOperatorKubernetesConfig(operatorKubernetesConfig);
		client.apps().deployments().inNamespace(operatorKubernetesConfig.getNamespace())
				.createOrReplace(operatorKubernetesBuilder.getDeployment());
	}

	@Override
	public void closeService(Schedule schedule) {
		client.apps().deployments().inNamespace(operatorKubernetesConfig.getNamespace()).withName(schedule.getNodeName()).delete();
	}

	@Override
	public void createOperators(Schedule schedule) {
		SessionOperatorKubernetesBuilder operatorKubernetesBuilder = new SessionOperatorKubernetesBuilder();
		operatorKubernetesBuilder.setSchedule(schedule);
		operatorKubernetesBuilder.setOperatorKubernetesConfig(operatorKubernetesConfig);
		client.batch().v1().jobs().inNamespace(operatorKubernetesConfig.getNamespace())
				.createOrReplace(operatorKubernetesBuilder.getJob());

	}

	@Override
	public void uninstallOperators(Schedule schedule) {
		client.batch().v1().jobs().inNamespace(operatorKubernetesConfig.getNamespace()).withName(schedule.getNodeName()).delete();
	}
}
