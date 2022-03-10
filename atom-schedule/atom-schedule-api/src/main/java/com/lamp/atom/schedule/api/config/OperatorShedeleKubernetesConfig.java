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
public class OperatorShedeleKubernetesConfig {

	private boolean isUser = false;
	
	private String masterUrl;
	
	private String configName  = "kubernetesConfig.yaml";
	
	private String configYaml;
		
	private String cpuContainerName = "githublaohu/atom-base-cpu:tf1.15-cpu";
	
	private String gpcContainerName = "githublaohu/atom-base-cpu:tf1.15-gpu";
	
	private String namespace = "default";
}
