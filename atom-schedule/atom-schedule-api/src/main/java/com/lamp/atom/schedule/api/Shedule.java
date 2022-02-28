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
package com.lamp.atom.schedule.api;

import java.util.Map;

import com.lamp.atom.schedule.api.deploy.Deploy;
import com.lamp.atom.schedule.api.strategy.Strategy;

import lombok.Data;

@Data
public class Shedule {

	private Strategy strategy;
	
	private Deploy deploy;
	
	private Map<String ,String> limits;
	
	private Map<String,String> hardwareConfig;
	
	private Map<String,String> runParameter;
	
	private Map<String,String> envs;
	
	private Map<String,String> labels;
	
	private Object object;
	
	private String noteId;
	
	private String noteName;
	
	private String namespace;
}
