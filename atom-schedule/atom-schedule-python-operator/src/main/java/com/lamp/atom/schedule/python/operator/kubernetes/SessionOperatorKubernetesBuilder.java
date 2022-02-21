package com.lamp.atom.schedule.python.operator.kubernetes;

import java.util.ArrayList;
import java.util.Collections;
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
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import io.fabric8.kubernetes.api.model.batch.v1.JobFluent.MetadataNested;
import io.fabric8.kubernetes.api.model.batch.v1.JobFluent.SpecNested;
import lombok.Setter;

public class SessionOperatorKubernetesBuilder {

	@Setter
	private OperatorShedeleKubernetesConfig operatorKubernetesConfig;
	
	@Setter
	private Shedule shedule;

	private JobBuilder job = new JobBuilder();

	
	
	private void job() {
		job.withApiVersion("batch/v1");
		// 训练 是job volcano
		// 推理 是deployment , 推理还有扩容，缩容 ， 起多少个，都不一样呀
		job.withKind("Job");
	}

	private void metadata() {
		MetadataNested<JobBuilder> metadata = job.withNewMetadata();
		// nvidia.com/gpu
		metadata
				// GPU 还是cpu镜像
				.withName("atom-runtime-session-"+this.shedule.getNoteName())
				// 标签，需要几个
				// 第一个 算子的名字+序列
				// 的哥
				.withLabels(Collections.singletonMap("label1", "maximum-length-of-63-characters"));
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
		SpecNested<JobBuilder> spec = job.withNewSpec();
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
	
	public Job getJob() {
		this.job();
		this.metadata();
		this.spec();
		return this.job.build();
	}
}
