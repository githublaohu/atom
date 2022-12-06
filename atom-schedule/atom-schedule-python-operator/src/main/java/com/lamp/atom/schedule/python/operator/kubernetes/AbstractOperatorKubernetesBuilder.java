package com.lamp.atom.schedule.python.operator.kubernetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.api.config.OperatorScheduleKubernetesConfig;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.EnvVarSource;
import io.fabric8.kubernetes.api.model.ObjectFieldSelector;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import lombok.Setter;

public abstract class AbstractOperatorKubernetesBuilder {

	@Setter
	OperatorScheduleKubernetesConfig operatorKubernetesConfig;

	@Setter
	Schedule schedule;

	ResourceRequirements resourceRequirements = new ResourceRequirements();

	List<EnvVar> envList = new ArrayList<EnvVar>();
	
	abstract String getModel();

	void resourceRequirements() {
		Map<String, Quantity> requests = new HashMap<>();
		resourceRequirements.setRequests(requests);
		for (Entry<String, String> e : schedule.getHardwareConfig().entrySet()) {
			requests.put(e.getKey(), new Quantity(e.getValue()));
		}

		Map<String, Quantity> limits = new HashMap<>();
		resourceRequirements.setLimits(limits);
		for (Entry<String, String> e : schedule.getLimits().entrySet()) {
			limits.put(e.getKey(), new Quantity(e.getValue()));
		}
	}

	void env() {

		for (Entry<String, String> e : schedule.getEnvs().entrySet()) {
			envList.add(new EnvVar(e.getKey(), e.getValue(), null));
		}
		envList.add(new EnvVar("docker", "true", null));
		envList.add(new EnvVar("runtime_model", this.getModel(), null));
		envList.add(new EnvVar("operator-data", JSON.toJSONString(schedule.getObject()), null));
		ObjectFieldSelector podIP = new ObjectFieldSelector();
		podIP.setFieldPath("status.hostIP");
		podIP.setApiVersion("v1");
		EnvVarSource nodeIp = new EnvVarSource();
		nodeIp.setFieldRef(podIP);
		envList.add(new EnvVar("node_ip", null, nodeIp));

		ObjectFieldSelector podName = new ObjectFieldSelector();
		podName.setFieldPath("metadata.name");
		podName.setApiVersion("v1");
		EnvVarSource nodeName = new EnvVarSource();
		nodeName.setFieldRef(podName);
		envList.add(new EnvVar("pod_name", null, nodeName));
	}

	String getIamges() {
		return schedule.getHardwareConfig().get("nvidia.com/gpu") == null
				? this.operatorKubernetesConfig.getCpuContainerName()
				: this.operatorKubernetesConfig.getGpcContainerName();
	}
}
