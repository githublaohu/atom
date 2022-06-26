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
package com.lamp.atom.schedule.api.config;

import lombok.Data;


@Data
public class OperatorScheduleKubernetesConfig {

	private boolean isUser = false;
	
	private String masterUrl;
	
	private String configName  = "kubernetesConfig.yaml";
	
	private String configYaml;
		
	private String cpuContainerName = "githublaohu/atom-base-cpu:tf1.15-cpu";
	
	private String gpcContainerName = "githublaohu/atom-base-cpu:tf1.15-gpu";
	
	private String namespace = "default";

	public boolean isUser() {
		return isUser;
	}

	public void setUser(boolean user) {
		isUser = user;
	}

	public String getMasterUrl() {
		return masterUrl;
	}

	public void setMasterUrl(String masterUrl) {
		this.masterUrl = masterUrl;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigYaml() {
		return configYaml;
	}

	public void setConfigYaml(String configYaml) {
		this.configYaml = configYaml;
	}

	public String getCpuContainerName() {
		return cpuContainerName;
	}

	public void setCpuContainerName(String cpuContainerName) {
		this.cpuContainerName = cpuContainerName;
	}

	public String getGpcContainerName() {
		return gpcContainerName;
	}

	public void setGpcContainerName(String gpcContainerName) {
		this.gpcContainerName = gpcContainerName;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
