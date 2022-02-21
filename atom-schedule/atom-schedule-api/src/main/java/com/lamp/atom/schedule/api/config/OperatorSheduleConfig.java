package com.lamp.atom.schedule.api.config;

import lombok.Data;

@Data
public class OperatorSheduleConfig {

	private OperatorShedeleKubernetesConfig operatorKubernetesConfig;
	
	private OperatorShedeleRpcConfig operatorShedeleRpcConfig;
}
