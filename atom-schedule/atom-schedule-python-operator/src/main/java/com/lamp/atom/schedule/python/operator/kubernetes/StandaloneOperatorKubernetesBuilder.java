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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.lamp.atom.schedule.api.Shedule;
import com.lamp.atom.schedule.api.config.OperatorShedeleKubernetesConfig;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentFluent.MetadataNested;
import io.fabric8.kubernetes.api.model.apps.DeploymentFluent.SpecNested;
import lombok.Setter;

/**
 * 主要用于推理,晚上
 * @author laohu
 *
 */
public class StandaloneOperatorKubernetesBuilder {

	@Setter
	private OperatorShedeleKubernetesConfig operatorKubernetesConfig;
	
	@Setter
	private Shedule shedule;

	private DeploymentBuilder job = new DeploymentBuilder();

	private void job() {
		job.withApiVersion("apps/v1");
		// 训练 是job volcano
		// 推理 是deployment , 推理还有扩容，缩容 ， 起多少个，都不一样呀
		job.withKind("Deployment");
	}

	private void metadata() {
		MetadataNested<DeploymentBuilder> metadata = job.withNewMetadata();
		// nvidia.com/gpu
		metadata
				// 这里名字如何命名，
				// 全局名
			    // 空间名
				// 场景名
				// TODO 真麻烦，好难。
				.withName("atom-runtime-standalone" + this.shedule.getNoteName())
				// 标签，需要几个
				//
				.withLabels(this.shedule.getLabels());
		metadata.endMetadata();
	}

	public void spec() {
		ResourceRequirements resourceRequirements = new ResourceRequirements();
		Map<String, Quantity> requests = new HashMap<>();
		resourceRequirements.setRequests(requests);
		for (Entry<String, String> e : shedule.getHardwareConfig().entrySet()) {
			requests.put(e.getKey(), new Quantity(e.getKey()));
		}

		Map<String, Quantity> limits = new HashMap<>();
		resourceRequirements.setLimits(limits);
		for (Entry<String, String> e : shedule.getLimits().entrySet()) {
			limits.put(e.getKey(), new Quantity(e.getKey()));
		}
		List<EnvVar> envList = new ArrayList<EnvVar>();
		for(Entry<String, String> e : shedule.getEnvs().entrySet()) {
			envList.add(new EnvVar(e.getKey(), e.getValue(), null));
		}
		envList.add(new EnvVar("runtime_model", "standalone", null));
		String value = shedule.getHardwareConfig().get("nvidia.com/gpu");
		SpecNested<DeploymentBuilder> spec = job.withNewSpec();
		spec.withReplicas(this.shedule.getDeploy().getCount());
		spec.withNewTemplate().withNewSpec().withHostNetwork(true).addNewContainer().withName(this.shedule.getNoteName())
				.withImage(Objects.isNull(value)
						? this.operatorKubernetesConfig.getCpuContainerName()
						: this.operatorKubernetesConfig.getGpcContainerName())
				.withResources(resourceRequirements)
				.withEnv(envList)
				.endContainer()
				.endSpec()
				.endTemplate()
				.endSpec();
	}
	
	public Deployment getDeployment() {
		this.job();
		this.metadata();
		this.spec();
		return job.build();
	}
}
