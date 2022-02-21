package com.lamp.atom.schedule.api.config;

import lombok.Data;


@Data
public class OperatorShedeleKubernetesConfig {

	private String masterUrl;
	
	private String configYaml;
		
	private String cpuContainerName;
	
	private String gpcContainerName;
	
	private String getPortMode;
	
	private String namespace;
}
