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

import java.util.List;
import java.util.Objects;

import com.alibaba.nacos.api.naming.NamingService;
import com.lamp.atom.schedule.api.AtomOperatorShedule;
import com.lamp.atom.schedule.api.AtomServiceShedule;
import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.ScheduleCallback;
import com.lamp.atom.schedule.api.config.OperatorScheduleKubernetesConfig;

import com.lamp.atom.schedule.api.deploy.AtomInstances;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * 普通job 定时job 批量job Resource cpu:2 nvidia.com/gpu: 2
 * 
 * @author laohu
 *
 */
@Slf4j
public class OperatorKubernetesSchedule implements AtomOperatorShedule, AtomServiceShedule {

	@Value("${nacos.config.server-addr}")
	private String nacosAddr;
	@Value("${nacos.config.namespace}")
	private String namespace;

	NamingService namingService = null;

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
	public ScheduleCallback createOperators(Schedule schedule) {
		SessionOperatorKubernetesBuilder operatorKubernetesBuilder = new SessionOperatorKubernetesBuilder();
		operatorKubernetesBuilder.setSchedule(schedule);
		operatorKubernetesBuilder.setOperatorKubernetesConfig(operatorKubernetesConfig);

		try {
			client.batch().v1().jobs().inNamespace(operatorKubernetesConfig.getNamespace())
					.createOrReplace(operatorKubernetesBuilder.getJob());

			// 注册服务
			List<AtomInstances> instancesList = schedule.getDeploy().getInstancesList();
			for (AtomInstances atomInstance: instancesList) {
				namingService.registerInstance("atom-runtime-kubernetes-service", atomInstance.getIp(), atomInstance.getPort());
			}

			return ScheduleCallback.OK;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ScheduleCallback.FAIL;
		}
	}

	@Override
	public void uninstallOperators(Schedule schedule) {
		client.batch().v1().jobs().inNamespace(operatorKubernetesConfig.getNamespace()).withName(schedule.getNodeName()).delete();
	}
}
